package me.javicabanas.randomuser.usercatalog.userlist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
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
import me.javicabanas.randomuser.androidtestcommons.AcceptanceTest
import me.javicabanas.randomuser.androidtestcommons.recyclerview.clickOnChildWithId
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.R
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import me.javicabanas.randomuser.usercatalog.userdetail.UserDetailActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
class UserListActivityTest : AcceptanceTest<UserListActivity>(UserListActivity::class.java) {
    @get:Rule
    var rule = HiltAndroidRule(this)
    val repository: UserRepository = mockk {
        every { getUser(any()) } returns UserMother.user.toRight()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    inner class FakeRepositoryModule {
        @Provides
        @Singleton
        fun provideRepository(): UserRepository = repository
    }

    @Test
    fun showsErrorStateIfThereAreNoUsers() {
        givenThereAreNoUsers()
        startActivity()
        onView(withId(R.id.notFoundImage)).check(matches(isDisplayed()))
    }

    private fun givenThereAreNoUsers() {
        every { repository.getAllUsers() } returns emptyList<User>().toRight()
    }

    @Test
    fun showsExactNumberOfUsers() {
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
        val users = givenThereAreUsers()
        startActivity()
        RecyclerViewInteraction.onRecyclerView<User>(withId(R.id.userRecyclerView))
            .withItems(users)
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
        val users = givenThereAreUsers()
        startActivity()
        RecyclerViewInteraction.onRecyclerView<User>(withId(R.id.userRecyclerView))
            .withItems(users)
            .check { user, view, exception ->
                matches(hasDescendant(withText(user.city))).check(
                    view,
                    exception
                )
            }
    }

    @Test
    fun doesNotShowErrorStateIfThereAreUsers() {
        givenThereAreUsers()
        startActivity()
        onView(withId(R.id.notFoundImage)).check(matches(not(isDisplayed())))
    }

    @Test
    fun doesNotShowLoadingIfThereAreUsers() {
        givenThereAreUsers()
        startActivity()
        onView(withId(R.id.progressIndicator)).check(matches(not(isDisplayed())))
    }

    @Test
    fun doesNotShowLoadingIfThereIsAnError() {
        givenThereAreNoUsers()
        startActivity()
        onView(withId(R.id.progressIndicator)).check(matches(not(isDisplayed())))
    }

    @Test
    fun opensUserDetailWhenClickOnListItem() {
        val users = givenThereAreUsers()
        val itemIndex = 0
        startActivity()

        onView(withId(R.id.userRecyclerView))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(itemIndex, click()))
        val userSelected = users[itemIndex]
        intended(hasComponent(UserDetailActivity::class.java.canonicalName))
        intended(hasExtra("userId", userSelected.id))
    }

    @Test
    fun deletesUserWhenClickOnButton() {
        val itemIndex = 0
        val users = givenThereAreUsersAndOneWillBeDeleted(itemIndex)
        startActivity()
        onView(withId(R.id.userRecyclerView))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    itemIndex,
                    clickOnChildWithId(R.id.deleteUserButton)
                )
            )
        onView(withId(R.id.userRecyclerView)).check(
            matches(RecyclerViewItemsCountMatcher(users.size - 1))
        )
    }

    private fun givenThereAreUsersAndOneWillBeDeleted(itemIndex: Int): List<User> {
        val initialUsers = UserMother.users
        every { repository.getAllUsers() } answers {
            initialUsers.toRight()
        } andThen {
            (initialUsers - initialUsers[itemIndex]).toRight()
        }
        every { repository.deleteUser(initialUsers[itemIndex].id) } returns Unit.toRight()
        return initialUsers
    }
}
