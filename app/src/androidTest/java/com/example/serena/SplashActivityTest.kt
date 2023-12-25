package com.example.serena


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class SplashActivityTest {
    @Test
    fun test_activityView() {
        ActivityScenario.launch(SplashActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.splash_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

//    @Test
//    fun test_checkUserStatus() {
//        val countingIdlingResource = CountingIdlingResource("checkUserStatus")
//        IdlingRegistry.getInstance().register(countingIdlingResource)
//        val scenario = ActivityScenario.launch(SplashActivity::class.java)
//        scenario.onActivity { activity ->
//            countingIdlingResource.increment()
//            activity.checkUserStatus()
//        }
//        IdlingRegistry.getInstance().unregister(countingIdlingResource)
//    }
}