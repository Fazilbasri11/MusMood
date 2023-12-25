package com.example.serena

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class WelcomeActivityTest{

    @Test
    fun test_activityView() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.welcome_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_visibleButton() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(ViewMatchers.withId(R.id.register))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun test_NavigationLogin() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.activity_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_NavigationRegister() {
        ActivityScenario.launch(WelcomeActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.register)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.activity_register))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}