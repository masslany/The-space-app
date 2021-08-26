package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.cache.entities.toStarlinkEntity
import com.masslany.thespaceapp.data.utils.networkBoundResource
import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.domain.repository.StarlinkRepository
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FetchStarlinksUseCase @Inject constructor(
    private val starlinksRepository: StarlinkRepository
) {

    @ExperimentalCoroutinesApi
    fun execute(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<StarlinkModel>>> = networkBoundResource(
        query = {
            starlinksRepository.getCachedStarlinks()
        },
        fetch = {
            starlinksRepository.fetchStarlinksData()
        },
        saveFetchResult = { result ->
            val entities = result.map {
                toStarlinkEntity(it)
            }
            starlinksRepository.saveFetchedStarlinks(entities)
        },
        shouldFetch = { cachedStarlinks ->
            if (forceRefresh) {
                true
            } else {
                val sorted = cachedStarlinks.sortedBy { starlink ->
                    starlink.updatedAt
                }
                val oldestTimestamp = sorted.firstOrNull()?.updatedAt
                val needsRefresh = oldestTimestamp == null ||
                        oldestTimestamp < System.currentTimeMillis() -
                        TimeUnit.DAYS.toMillis(1)

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