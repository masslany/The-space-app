package com.masslany.thespaceapp.presentation.launches

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LaunchesFragmentTest {

    private val robot = LaunchesFragmentTestRobot()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

}