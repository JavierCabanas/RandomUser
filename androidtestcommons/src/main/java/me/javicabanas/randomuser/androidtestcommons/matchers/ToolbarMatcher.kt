package me.javicabanas.randomuser.androidtestcommons.matchers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

object ToolbarMatcher {
    fun onCollapsingToolbarWithTitle(title: CharSequence): ViewInteraction =
        onView(isAssignableFrom(CollapsingToolbarLayout::class.java)).check(
            matches(withCollapsingToolbarTitle(CoreMatchers.`is`<CharSequence>(title)))
        )

    private fun withCollapsingToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> {
        return object :
            BoundedMatcher<Any, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
            public override fun matchesSafely(toolbar: CollapsingToolbarLayout): Boolean =
                textMatcher.matches(toolbar.title)

            override fun describeTo(description: Description) {
                description.appendText("with collapsing toolbar title: ")
                textMatcher.describeTo(description)
            }
        }
    }
}