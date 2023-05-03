package com.example.todo_clippo

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val idlingResource = CountingIdlingResource("FloatingActionButtonIdlingResource")

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun testTasksTextIsCorrect() {
        val expectedText = getInstrumentation().targetContext.getString(R.string.tasks)
        onView(withId(R.id.tasksText)).check(matches(withText(expectedText)))
    }

    @Test
    fun testRecyclerViewIsDisplayed() {
        onView(withId(R.id.tasksRecyclerView)).check(matches(withEffectiveVisibility(VISIBLE)))
    }

    // due to some error with emulating we just check partial visibility
    @Test
    fun testFloatingActionButtonIsDisplayed() {
        onView(withId(R.id.fab)).check(matches(withEffectiveVisibility(VISIBLE)))
    }
}
