package com.masslany.thespaceapp.domain.usecase

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.masslany.thespaceapp.data.local.cache.entities.toLaunchEntity
import com.masslany.thespaceapp.data.remote.response.launches.Core
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
internal class FetchLaunchesDataUseCaseTest {
    private val repository: LaunchesRepository = mockk()

    @Test
    fun emptyResponseReturnsResourceLoadingThenSuccess() = runBlockingTest {
        // Given
        every { repository.getCachedLaunches() } returns flowOf(emptyList())
        coEvery { repository.fetchLaunchesData() } returns emptyList()
        coEvery { repository.saveFetchedLaunches(any()) } returns Unit
        val useCase = FetchLaunchesDataUseCase(repository)

        // When
        val result = useCase.execute(
            forceRefresh = true,
            onFetchSuccess = {},
            onFetchFailed = {}
        ).toList()

        // Then
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        assertThat((result[1] as Resource.Success<List<LaunchModel>>).data).isEmpty()
    }

    @Test
    fun exceptionThrownFromApiReturnsResourceLoadingThenError() = runBlockingTest {
        // Given
        every { repository.getCachedLaunches() } returns flowOf(emptyList())
        coEvery { repository.fetchLaunchesData() } throws IOException()
        coEvery { repository.saveFetchedLaunches(any()) } returns Unit
        val useCase = FetchLaunchesDataUseCase(repository)

        // When
        val result = useCase.execute(
            forceRefresh = true,
            onFetchSuccess = {},
            onFetchFailed = {}
        ).toList()

        // Then
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Error::class.java)
        assertThat((result[1] as Resource.Error).throwable)
            .hasCauseThat().isSameInstanceAs(IOException().cause)
    }

    @Test
    fun apiResponseShouldBeCached() = runBlockingTest {
        // Given
        val models = listOf(
            createLaunchModel(id = "1"),
            createLaunchModel(id = "2")
        )
        val entities = models.map {
            toLaunchEntity(it)
        }

        every { repository.getCachedLaunches() } returns flowOf(models)
        coEvery { repository.fetchLaunchesData() } returns models
        coEvery { repository.saveFetchedLaunches(entities) } returns Unit
        val useCase = FetchLaunchesDataUseCase(repository)

        // When
        val result = useCase.execute(
            forceRefresh = true,
            onFetchSuccess = {},
            onFetchFailed = {}
        ).toList()

        // Then
        assertThat(result[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(result[1]).isInstanceOf(Resource.Success::class.java)
        assertThat((result[1] as Resource.Success<List<LaunchModel>>).data).containsExactlyElementsIn(models)

        coVerifyOrder {
            repository.getCachedLaunches()
            repository.fetchLaunchesData()
            repository.saveFetchedLaunches(entities)
        }
    }

    private fun createLaunchModel(
        id: String = "id1",
        name: String = "Starlink 22",
        details: String? = null,
        date: Long = 31243252345,
        image: String? = null,
        rocketId: String? = null,
        launchpadId: String? = null,
        cores: List<Core> = emptyList(),
        crewIds: List<String> = emptyList(),
        payloadsIds: List<String> = emptyList(),
        webcast: Uri? = null,
        article: Uri? = null,
        wikipedia: Uri? = null,
        updatedAt: Long = 0
    ) = LaunchModel(
        id = id,
        name = name,
        details = details,
        date = date,
        image = image,
        rocketId = rocketId,
        launchpadId = launchpadId,
        cores = cores,
        crewIds = crewIds,
        payloadsIds = payloadsIds,
        webcast = webcast,
        article = article,
        wikipedia = wikipedia,
        updatedAt = updatedAt
    )
}