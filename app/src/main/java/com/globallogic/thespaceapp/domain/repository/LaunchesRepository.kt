package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.utils.Result

interface LaunchesRepository {
    suspend fun fetchUpcomingLaunchesData(): Result<List<LaunchEntity>>

    suspend fun fetchLaunchById(id: String): Result<LaunchEntity>
}