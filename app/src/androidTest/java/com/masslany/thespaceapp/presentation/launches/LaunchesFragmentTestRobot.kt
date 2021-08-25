package com.masslany.thespaceapp.presentation.launches

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.masslany.thespaceapp.utils.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LaunchesFragmentTestRobot {

    companion object {
//        private val searchMenuItem = onView(ViewMatchers.withId(R.id.search_item))
    }

    private val targetContext = ApplicationProvider.getApplicationContext<Context>()

    fun setupFragment() = apply {
        launchFragmentInHiltContainer<LaunchesFragment> {

        }
    }
}