package com.masslany.thespaceapp.domain.usecase

import com.google.common.truth.Truth
import com.masslany.thespaceapp.domain.model.CirclePreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@HiltAndroidTest
@RunWith(JUnit4::class)
class UpdateStarlinkPreferencesUseCaseTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var updateStarlinkPreferencesUseCase: UpdateStarlinkPreferencesUseCase

    @Inject
    lateinit var getStarlinkPreferencesUseCase: GetStarlinkPreferencesUseCase

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Unconfined + job)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {
        job.cancel()
    }

    @Test
    fun shouldUpdateStarlinkPreferences() {
        // Given
        val preferences = CirclePreferencesModel(degrees = 33.0, showCoverage = true)

        // When
        scope.launch {
            updateStarlinkPreferencesUseCase.execute(preferences)
        }

        // Then
        scope.launch {
            val getStarlinkPreferencesUseCase = getStarlinkPreferencesUseCase.execute()
            val result = getStarlinkPreferencesUseCase.last()
            Truth.assertThat(result).isEqualTo(preferences)
        }

    }
}