package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.RoadsterEntity
import com.globallogic.thespaceapp.utils.Result

interface RoadsterRepository {
    suspend fun fetchRoadsterData(): Result<RoadsterEntity>
}