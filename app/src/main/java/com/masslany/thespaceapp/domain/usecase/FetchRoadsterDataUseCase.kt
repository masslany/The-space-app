package com.masslany.thespaceapp.domain.usecase

import androidx.room.withTransaction
import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import com.masslany.thespaceapp.data.local.cache.entities.toRoadsterEntity
import com.masslany.thespaceapp.data.local.cache.entities.toRoadsterModel
import com.masslany.thespaceapp.data.utils.networkBoundResource
import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.domain.repository.RoadsterRepository
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FetchRoadsterDataUseCase @Inject constructor(
    private val roadsterRepository: RoadsterRepository,
    private val database: CacheDatabase
) {
    private val roadsterDao = database.roadsterDao()

    @ExperimentalCoroutinesApi
    fun execute(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<RoadsterModel>> = networkBoundResource(
        query = {
            val roadster = roadsterDao.getRoadster()
            // Do no trust InteliJ, it can in fact be null somehow
            roadster.map {
                it?.let{toRoadsterModel(it)}
            }
        },
        fetch = {
            roadsterRepository.fetchRoadsterData()
        },
        saveFetchResult = { result ->
            val data = (result as Resource.Success).data

            database.withTransaction {
                roadsterDao.deleteRoadster()
                roadsterDao.insertRoadster(toRoadsterEntity(data))
            }
        },
        shouldFetch = { cachedRoadster ->
            if(forceRefresh) {
                true
            } else {
                val oldestTimestamp = cachedRoadster?.updatedAt ?: null
                val needsRefresh = oldestTimestamp == null ||
                        oldestTimestamp < System.currentTimeMillis() -
                        TimeUnit.HOURS.toMillis(3)
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