package com.masslany.thespaceapp.presentation.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.R
import com.masslany.thespaceapp.data.local.cache.entities.toLaunchEntity
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.domain.usecase.FetchLaunchesDataUseCase
import com.masslany.thespaceapp.utils.MainCoroutineRule
import com.masslany.thespaceapp.utils.State
import com.masslany.thespaceapp.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.util.*


@ExperimentalCoroutinesApi
class LaunchesViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LaunchesViewModel
    private val repository: LaunchesRepository = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun emptyResponseShouldContainsFourItems() = runBlockingTest {
        // Given
        every { repository.getCachedLaunches() } returns flowOf(emptyList())
        coEvery { repository.fetchLaunchesData() } returns emptyList()
        coEvery { repository.saveFetchedLaunches(emptyList()) } returns Unit
        viewModel = LaunchesViewModel(
            FetchLaunchesDataUseCase(repository)
        )

        // When
        viewModel.fetchUpcomingLaunchesData(true)

        // Then
        val result = viewModel.launches.getOrAwaitValue()
        assertThat(result).isInstanceOf(State.Success::class.java)
        // Upcoming header - No items message - Past header - No items message
        assertThat((result as State.Success).data.size).isEqualTo(4)
    }

    @Test
    fun successResponseShouldContainItem() = runBlockingTest {
        // Given
        val item = LaunchModel(
            id = UUID.randomUUID().toString(), "Test", null, 123, null,
            null, null, emptyList(), emptyList(), emptyList(),
            null, null, null
        )
        coEvery { repository.fetchLaunchesData() } returns listOf(item)
        coEvery { repository.saveFetchedLaunches(listOf(toLaunchEntity(item))) } returns Unit
        every { repository.getCachedLaunches() } returns flowOf(listOf(item))
        viewModel = LaunchesViewModel(
            FetchLaunchesDataUseCase(repository)
        )

        // When
        viewModel.fetchUpcomingLaunchesData(true)

        // Then
        val result = viewModel.launches.getOrAwaitValue()
        val expectedItem = LaunchAdapterItem(
            type = R.id.item_recyclerview,
            header = null,
            launchModel = item
        )
        assertThat(result).isInstanceOf(State.Success::class.java)
        assertThat((result as State.Success).data).contains(expectedItem)
    }

    @Test
    fun errorResponseShouldBeHandled() = runBlockingTest {
        // Given
        coEvery { repository.fetchLaunchesData() } throws IOException()
        coEvery { repository.saveFetchedLaunches(emptyList()) } returns Unit
        every { repository.getCachedLaunches() } returns flowOf(emptyList())
        viewModel = LaunchesViewModel(
            FetchLaunchesDataUseCase(repository)
        )

        // When
        viewModel.fetchUpcomingLaunchesData(true)

        //Then
        val result = viewModel.launches.getOrAwaitValue()
        assertThat(result).isInstanceOf(State.Error::class.java)
    }
}