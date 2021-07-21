package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.LaunchesEntity
import com.globallogic.thespaceapp.utils.Result

interface LaunchesRepository {
    suspend fun fetchUpcomingLaunchesData(): Result<List<LaunchesEntity>>
}