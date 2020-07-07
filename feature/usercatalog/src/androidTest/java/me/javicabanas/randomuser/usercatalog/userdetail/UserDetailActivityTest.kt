package me.javicabanas.randomuser.usercatalog.userdetail

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
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
import me.javicabanas.randomuser.androidtestcommons.AcceptanceTest
import me.javicabanas.randomuser.androidtestcommons.matchers.ToolbarMatcher.onCollapsingToolbarWithTitle
import me.javicabanas.randomuser.core.failure.Failure
import me.javicabanas.randomuser.core.functional.toLeft
import me.javicabanas.randomuser.core.functional.toRight
import me.javicabanas.randomuser.core.model.User
import me.javicabanas.randomuser.testcommons.UserMother
import me.javicabanas.randomuser.usercatalog.R
import me.javicabanas.randomuser.usercatalog.domain.data.UserRepository
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
class UserDetailActivityTest : AcceptanceTest<UserDetailActivity>(UserDetailActivity::class.java) {
    @get:Rule
    var rule = HiltAndroidRule(this)
    val repository: UserRepository = mockk {
        every { getAllUsers() } returns UserMother.users.toRight()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    inner class FakeRepositoryModule {
        @Provides
        @Singleton
        fun provideRepository(): UserRepository = repository
    }

    @Test
    fun showsUserNameAsToolbarTitle() {
        val user = givenAUser()
        startActivity(user)
        val title = "${user.firstName} ${user.lastName}"
        onCollapsingToolbarWithTitle(title).check(matches(isDisplayed()))
    }

    private fun givenAUser(): User {
        val user = UserMother.user
        every { repository.getUser(user.id) } returns user.toRight()
        return user
    }

    @Test
    fun showsUserGender() {
        val user = givenAUser()
        startActivity(user)
        scrollToView(R.id.gender)
        onView(withText(user.gender)).check(matches(isDisplayed()))
    }

    @Test
    fun showsUserEmail() {
        val user = givenAUser()
        startActivity(user)
        scrollToView(R.id.email)
        onView(withText(user.email)).check(matches(isDisplayed()))
    }

    @Test
    fun showsUserCity() {
        val user = givenAUser()
        startActivity(user)
        scrollToView(R.id.city)
        onView(withText(user.city)).check(matches(isDisplayed()))
    }

    @Test
    fun showsUserDescription() {
        val user = givenAUser()
        startActivity(user)
        scrollToView(R.id.description)
        onView(withText(user.description)).check(matches(isDisplayed()))
    }

    @Test
    fun doesNotShowLoadingIfThereIsAUser() {
        val user = givenAUser()
        startActivity(user)
        onView(withId(R.id.progressIndicator))
            .check(matches(CoreMatchers.not(isDisplayed())))
    }

    @Test
    fun doesNotShowLoadingIfThereIsAnError() {
        givenThereIsNoUser()
        startActivity(UserMother.user)
        onView(withId(R.id.progressIndicator))
            .check(matches(CoreMatchers.not(isDisplayed())))
    }

    private fun givenThereIsNoUser() {
        every { repository.getUser(any()) } returns Failure.ElementNotFound("").toLeft()
    }

    private fun startActivity(user: User) {
        val args = Bundle()
        args.putString("userId", user.id)
        startActivity(args)
    }

    private fun scrollToView(viewId: Int) {
        onView(withId(viewId)).perform(scrollTo())
    }
}
