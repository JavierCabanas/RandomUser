package me.javicabanas.randomuser.usercatalog.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.CoroutinesTestRule
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.domain.GetUser
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {
    @get:Rule
    val coroutinesRule: TestRule = CoroutinesTestRule()

    @get:Rule
    val liveDataRule: TestRule = InstantTaskExecutorRule()
    private val getUser: GetUser = mockk()
    private lateinit var viewModel: UserDetailViewModel
    private val mockObserver: Observer<UserDetailViewState> = mockk(relaxed = true)

    @Before
    fun setupViewModel() {
        viewModel = UserDetailViewModel(getUser)
        viewModel.viewState.observeForever(mockObserver)
    }

    @After
    fun stopObserver() {
        viewModel.viewState.observeForever(mockObserver)
    }

    @Test
    fun `should lead to WithData state when users are retrieved`() {
        val user = givenAUser()
        val uiModel = user.toDetailUi()
        viewModel.loadUser(user.id)
        assert(viewModel.viewState.value == UserDetailViewState.WithData(uiModel))
    }

    private fun givenAUser(): User {
        val user = UserMother.user
        coEvery { getUser(user.id) } returns user.toRight()
        return user
    }

    @Test
    fun `should lead to Error state when a failure is received`() {
        givenAFailureResponse()
        viewModel.loadUser(UserMother.user.id)
        assert(viewModel.viewState.value == UserDetailViewState.Error)
    }

    private fun givenAFailureResponse() {
        coEvery { getUser(any()) } returns Failure.ElementNotFound("").toLeft()
    }

    @Test
    fun `loading state is setted while getting users`() {
        val user = givenAUser()
        viewModel.loadUser(user.id)
        verify { mockObserver.onChanged(UserDetailViewState.Loading) }
    }

    @Test
    fun `should lead to Error state when request id is null`() {
        viewModel.loadUser(null)
        assert(viewModel.viewState.value == UserDetailViewState.Error)
    }
}