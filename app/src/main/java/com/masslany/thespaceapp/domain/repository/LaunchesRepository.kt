package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    suspend fun fetchLaunchesData(): List<LaunchModel>

    suspend fun fetchLaunchById(id: String): Resource<LaunchModel>

    fun getCachedLaunches(): Flow<List<LaunchModel>>

    suspend fun saveFetchedLaunches(entities: List<LaunchEntity>)
}