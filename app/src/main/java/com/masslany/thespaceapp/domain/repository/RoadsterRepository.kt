package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.utils.Resource

interface RoadsterRepository {
    suspend fun fetchRoadsterData(): Resource<RoadsterModel>
}