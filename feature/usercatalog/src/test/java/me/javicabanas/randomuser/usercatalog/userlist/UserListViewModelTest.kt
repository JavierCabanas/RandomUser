package me.javicabanas.randomuser.usercatalog.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.testcommons.CoroutinesTestRule
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.domain.GetAllUsers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class UserListViewModelTest {
    @get:Rule
    val coroutinesRule: TestRule = CoroutinesTestRule()

    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()
    private val getAllUsers: GetAllUsers = mockk()
    private lateinit var viewModel: UserListViewModel
    private val mockObserver: Observer<UserListViewState> = mockk(relaxed = true)

    @Before
    fun setupViewModel() {
        viewModel = UserListViewModel(getAllUsers)
        viewModel.viewState.observeForever(mockObserver)
    }

    @After
    fun stopObserver() {
        viewModel.viewState.observeForever(mockObserver)
    }

    @Test
    fun `should lead to WithData state when users are retrieved`() {
        val users = givenAListWithUsers()
        viewModel.loadUsers()
        assert(viewModel.viewState.value == UserListViewState.WithData(users))
    }

    private fun givenAListWithUsers(): List<UserUiModel> {
        val users = UserMother.users
        coEvery { getAllUsers(Unit) } returns users.toRight()
        return users.map { it.toUi() }
    }

    @Test
    fun `should lead to Empty state when a failure is received`() {
        givenAFailureResponse()
        viewModel.loadUsers()
        assert(viewModel.viewState.value == UserListViewState.Empty)
    }

    private fun givenAFailureResponse() {
        coEvery { getAllUsers(Unit) } returns Failure.ElementNotFound("").toLeft()
    }

    @Test
    fun `loading state is setted while getting users`() {
        givenAListWithUsers()
        viewModel.loadUsers()
        verify { mockObserver.onChanged(UserListViewState.Loading) }
    }

}