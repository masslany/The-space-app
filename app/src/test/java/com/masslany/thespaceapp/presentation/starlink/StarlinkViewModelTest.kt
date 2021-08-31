package com.masslany.thespaceapp.presentation.starlink

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.domain.repository.StarlinkRepository
import com.masslany.thespaceapp.domain.usecase.FetchStarlinksUseCase
import com.masslany.thespaceapp.domain.usecase.GetStarlinkPreferencesUseCase
import com.masslany.thespaceapp.domain.usecase.UpdateStarlinkPreferencesUseCase
import com.masslany.thespaceapp.utils.MainCoroutineRule
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
internal class StarlinkViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: StarlinkViewModel
    private val repository: StarlinkRepository = mockk()
    private val fetchStarlinksUseCase: FetchStarlinksUseCase = FetchStarlinksUseCase(repository)
    private val getStarlinkPreferencesUseCase: GetStarlinkPreferencesUseCase = mockk()
    private val updateStarlinkPreferencesUseCase: UpdateStarlinkPreferencesUseCase = mockk()

    @Before
    fun setup() {
        every { getStarlinkPreferencesUseCase.execute() } returns emptyFlow()
        coEvery { updateStarlinkPreferencesUseCase.execute(any()) }
    }

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

        // Then
        val expected = 1179478.8062802572
        assertThat(result).isAtLeast(expected.toInt())
    }

    @Test
    fun emptyResponseShouldContainsEmptyMarkersList() = runBlockingTest {
        // Given
        val data = emptyList<StarlinkModel>()
        every { repository.getCachedStarlinks() } returns flowOf(data)
        coEvery { repository.fetchStarlinksData() } returns data
        coEvery { repository.saveFetchedStarlinks(emptyList()) } returns Unit
        viewModel = StarlinkViewModel(
            fetchStarlinksUseCase,
            getStarlinkPreferencesUseCase,
            updateStarlinkPreferencesUseCase,
            TestCoroutineDispatcher()
        )

        // Then
        val markerMap = viewModel.markersMap.getOrAwaitValue()
        // Converted all starlinks into markers
        assertThat(markerMap.entries.size).isEqualTo(data.size)
    }

    @Test
    fun validResponseShouldContainsEqualSizeMarkersList() = runBlockingTest {
        // Given
        val data = listOf(
            createStarlinkModel(id = "1"),
            createStarlinkModel(id = "2"),
            createStarlinkModel(id = "3")
        )
        every { repository.getCachedStarlinks() } returns flowOf(data)
        coEvery { repository.fetchStarlinksData() } returns data
        coEvery { repository.saveFetchedStarlinks(any()) } returns Unit
        viewModel = StarlinkViewModel(
            fetchStarlinksUseCase,
            getStarlinkPreferencesUseCase,
            updateStarlinkPreferencesUseCase,
            TestCoroutineDispatcher()
        )

        // Then
        val markerMap = viewModel.markersMap.getOrAwaitValue()
        // Converted all starlinks into markers
        assertThat(markerMap.entries.size).isEqualTo(data.size)
    }

    @Test
    fun errorResponseShouldBeHandled() = runBlockingTest {
        // Given
        val data = emptyList<StarlinkModel>()
        val exception = IOException()
        every { repository.getCachedStarlinks() } returns flowOf(data)
        coEvery { repository.fetchStarlinksData() } throws exception
        coEvery { repository.saveFetchedStarlinks(emptyList()) } returns Unit
        viewModel = StarlinkViewModel(
            fetchStarlinksUseCase,
            getStarlinkPreferencesUseCase,
            updateStarlinkPreferencesUseCase,
            TestCoroutineDispatcher()
        )

        // Then
        val starlinks = viewModel.starlinks.getOrAwaitValue()
        // Converted all starlinks into markers
        assertThat(starlinks).isInstanceOf(Resource.Error::class.java)
    }

    private fun createStarlinkModel(
        id: String = "id1",
        objectName: String = "Starlink-24",
        launchDate: String = "12.02.2020",
        TLELine0: String = "STARLINK-24             ",
        TLELine1: String = "1 44238U 19029D   21237.86036098  .00007126  00000-0  37494-3 0  9993",
        TLELine2: String = "2 44238  52.9979  31.4542 0001740 103.9964 256.1222 15.16678365123214",
        updatedAt: Long = 0
    ): StarlinkModel = StarlinkModel(
        id = id,
        objectName = objectName,
        launchDate = launchDate,
        TLELine0 = TLELine0,
        TLELine1 = TLELine1,
        TLELine2 = TLELine2,
        updatedAt = updatedAt
    )
}