package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.cache.entities.toLaunchEntity
import com.masslany.thespaceapp.data.utils.networkBoundResource
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FetchLaunchesDataUseCase @Inject constructor(
    private val launchesRepository: LaunchesRepository
) {

    @ExperimentalCoroutinesApi
    fun execute(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<LaunchModel>>> =
        networkBoundResource(
            query = {
                launchesRepository.getCachedLaunches()
            },
            fetch = {
                launchesRepository.fetchLaunchesData()
            },
            saveFetchResult = { result ->
                val entities = result.map {
                    toLaunchEntity(it)
                }
                launchesRepository.saveFetchedLaunches(entities)
            },
            shouldFetch = { cachedLaunches ->
                if (forceRefresh) {
                    true
                } else {
                    val sorted = cachedLaunches.sortedBy { launch ->
                        launch.updatedAt
                    }
                    val oldestTimestamp = sorted.firstOrNull()?.updatedAt
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.HOURS.toMillis(1)
                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t ->
                if (t !is HttpException && t !is IOException) {
                    throw t
                }
                onFetchFailed(t)
            }

        )
}