package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.RoadsterEntity
import com.masslany.thespaceapp.utils.Result

interface RoadsterRepository {
    suspend fun fetchRoadsterData(): Result<RoadsterEntity>
}