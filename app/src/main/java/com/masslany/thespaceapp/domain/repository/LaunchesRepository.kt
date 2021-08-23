package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    suspend fun fetchLaunchesData(): Resource<List<LaunchModel>>

    suspend fun fetchLaunchById(id: String): Result<LaunchModel>

    fun getCachedLaunches(): Flow<List<LaunchModel>>

    suspend fun saveFetchedLaunches(entities: List<LaunchEntity>)
}