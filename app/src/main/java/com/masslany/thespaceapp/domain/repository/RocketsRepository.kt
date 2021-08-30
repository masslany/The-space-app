package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.utils.Resource

interface RocketsRepository {
    suspend fun fetchRocketsData(): Resource<List<RocketModel>>

    suspend fun fetchRocketById(id: String): Resource<RocketModel>
}