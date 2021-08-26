package com.masslany.thespaceapp.presentation.launches

import com.masslany.thespaceapp.di.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@UninstallModules(RepositoryModule::class)
class LaunchesFragmentTest {

    private val launchesRobot = LaunchesFragmentTestRobot()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun enterValidQueryFindsItem() {
        launchesRobot
            .setupFragment()
            .clickSearchMenuItem()
            .typeQuery("Star")
            .assertItemIsVisible()
    }

    @Test
    fun enterInvalidQueryNoItemsAreFound() {
        launchesRobot
            .setupFragment()
            .clickSearchMenuItem()
            .typeQuery("An invalid query")
            .assertNoItemsFoundMessageVisible()
    }
}