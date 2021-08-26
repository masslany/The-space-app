package com.masslany.thespaceapp.presentation.starlink

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.domain.usecase.FetchStarlinksUseCase
import com.masslany.thespaceapp.domain.usecase.GetStarlinkPreferencesUseCase
import com.masslany.thespaceapp.domain.usecase.UpdateStarlinkPreferencesUseCase
import com.masslany.thespaceapp.utils.MainCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class StarlinkViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: StarlinkViewModel
    private val fetchStarlinksUseCase: FetchStarlinksUseCase = mockk()
    private val getStarlinkPreferencesUseCase: GetStarlinkPreferencesUseCase = mockk()
    private val updateStarlinkPreferencesUseCase: UpdateStarlinkPreferencesUseCase = mockk()

    @Test
    fun calculateRadiusReturnsCorrectValue() {
        // Given
        every { getStarlinkPreferencesUseCase.execute() } returns emptyFlow()

        viewModel = StarlinkViewModel(
            fetchStarlinksUseCase,
            getStarlinkPreferencesUseCase,
            updateStarlinkPreferencesUseCase,
            TestCoroutineDispatcher()
        )

        val radius = 25.0
        val altitude = 550.0

        // When
        val result = viewModel.calculateRadius(radius, altitude)

        val expected = 1179478.8062802572
        assertThat(result).isAtLeast(expected.toInt())
    }
}