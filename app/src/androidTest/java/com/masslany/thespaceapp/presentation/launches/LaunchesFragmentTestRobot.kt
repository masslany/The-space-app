package com.masslany.thespaceapp.presentation.launches

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.utils.launchFragmentInHiltContainer
import com.masslany.thespaceapp.utils.typeSearchViewText
import com.masslany.thespaceapp.utils.withViewAtPosition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.anyOf

@ExperimentalCoroutinesApi
class LaunchesFragmentTestRobot {

    companion object {
        private val searchMenuItem = onView(withId(R.id.search_item))
    }

    fun setupFragment() = apply {
        launchFragmentInHiltContainer<LaunchesFragment> {

        }
    }

    fun clickSearchMenuItem() = apply {
        searchMenuItem.perform(ViewActions.click())
    }

    fun typeQuery(query: String) = apply {
        searchMenuItem.perform(typeSearchViewText(query))
    }

    fun assertItemIsVisible() = apply {
        onView(withText("Starlink A")).check(matches(isDisplayed()))
    }

    fun assertNoItemsFoundMessageVisible() = apply {
        onView(withId(R.id.rv_launches))
            .check(
                matches(
                    withViewAtPosition(
                        1,
                        hasDescendant(anyOf(withId(R.id.item_recyclerview_empty), isDisplayed()))
                    )
                )
            )
        onView(withId(R.id.rv_launches))
            .check(
                matches(
                    withViewAtPosition(
                        3,
                        hasDescendant(anyOf(withId(R.id.item_recyclerview_empty), isDisplayed()))
                    )
                )
            )
    }
}