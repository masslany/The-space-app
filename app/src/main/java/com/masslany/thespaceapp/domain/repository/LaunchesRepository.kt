package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.LaunchEntity
import com.masslany.thespaceapp.utils.Result

interface LaunchesRepository {
    suspend fun fetchUpcomingLaunchesData(): Result<List<LaunchEntity>>

    suspend fun fetchLaunchById(id: String): Result<LaunchEntity>
}