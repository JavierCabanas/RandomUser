package me.javicabanas.randomuser.usercatalog.userlist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import me.javicabanas.randomuser.androidtestcommons.matchers.RecyclerViewItemsCountMatcher
import me.javicabanas.randomuser.androidtestcommons.recyclerview.RecyclerViewInteraction
import me.javicabanas.randomuser.androidtestcommons.runner.AcceptanceTest
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.R
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
class UserListActivityTest : AcceptanceTest<UserListActivity>(UserListActivity::class.java) {
    @get:Rule
    var rule = HiltAndroidRule(this)
    val repository: UserRepository = UserRepository(mockk())

    @Module
    @InstallIn(ApplicationComponent::class)
    inner class FakeBarModule {
        @Provides
        @Singleton
        fun provideRepository(): UserRepository = repository
    }

    @Test
    fun showsErrorStateIfThereAreNoSuperHeroes() {
        givenThereAreNoUsers()
        startActivity()
        onView(withId(R.id.notFoundImage)).check(matches(isDisplayed()))
    }

    private fun givenThereAreNoUsers() {
        every { repository.getAllUsers() } returns emptyList<User>().toRight()
    }

    @Test
    fun showsExactNumberOfSuperHeroes() {
        val userList = givenThereAreUsers()
        startActivity()
        onView(withId(R.id.userRecyclerView)).check(
            matches(RecyclerViewItemsCountMatcher(userList.size))
        )
    }

    private fun givenThereAreUsers(): List<User> {
        val users = UserMother.users
        every { repository.getAllUsers() } returns users.toRight()
        return users
    }

    @Test
    fun showsUsersNameIfThereUsers() {
        val superHeroes = givenThereAreUsers()
        startActivity()
        RecyclerViewInteraction.onRecyclerView<User>(withId(R.id.userRecyclerView))
            .withItems(superHeroes)
            .check { user, view, exception ->
                val expectedName = "${user.firstName} ${user.lastName}"
                matches(hasDescendant(withText(expectedName))).check(
                    view,
                    exception
                )
            }
    }

    @Test
    fun showsUsersCityIfThereUsers() {
        val superHeroes = givenThereAreUsers()
        startActivity()
        RecyclerViewInteraction.onRecyclerView<User>(withId(R.id.userRecyclerView))
            .withItems(superHeroes)
            .check { user, view, exception ->
                matches(hasDescendant(withText(user.city))).check(
                    view,
                    exception
                )
            }
    }

    @Test
    fun doesNotShowErrorStateIfThereAreUsers() {
        val superHeroes = givenThereAreUsers()
        startActivity()
        onView(withId(R.id.notFoundImage)).check(matches(not(isDisplayed())))
    }

    @Test
    fun doesNotShowLoadingIfThereAreUsers() {
        val superHeroes = givenThereAreUsers()
        startActivity()
        onView(withId(R.id.progressIndicator)).check(matches(not(isDisplayed())))
    }
}
