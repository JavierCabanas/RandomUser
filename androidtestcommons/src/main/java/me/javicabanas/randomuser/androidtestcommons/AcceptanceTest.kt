package me.javicabanas.randomuser.androidtestcommons

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@Suppress("UnnecessaryAbstractClass")
abstract class AcceptanceTest<T : Activity>(clazz: Class<T>) {

    @get:Rule
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, false)

    @Before
    fun setup() {
        Dispatchers.resetMain()
    }

    fun startActivity(args: Bundle = Bundle()): T {
        val intent = Intent()
        intent.putExtras(args)
        return testRule.launchActivity(intent)
    }
}
