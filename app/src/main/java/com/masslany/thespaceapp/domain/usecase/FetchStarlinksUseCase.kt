package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.cache.dao.StarlinkDao
import com.masslany.thespaceapp.data.utils.networkBoundResource
import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.domain.repository.StarlinkRepository
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FetchStarlinksUseCase @Inject constructor(
    private val starlinksRepository: StarlinkRepository,
    private val starlinksDao: StarlinkDao
) {
    @ExperimentalCoroutinesApi
    fun execute(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<StarlinkModel>>> = networkBoundResource(
        query = {
            starlinksDao.getStarlinks().map {
                val list = mutableListOf<StarlinkModel>()
                it.forEach {  entity ->
                    list.add(entity.toStarlinkModel(entity))
                }
                list
            }
        },
        fetch = {
            starlinksRepository.fetchStarlinksData()
        },
        saveFetchResult = { result ->
            (result as Resource.Success).data
        },
        shouldFetch = {
              forceRefresh
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