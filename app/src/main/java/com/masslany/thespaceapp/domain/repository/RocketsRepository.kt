package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.utils.Result

interface RocketsRepository {
    suspend fun fetchRocketsData(): Result<List<RocketModel>>

    suspend fun fetchRocketById(id: String): Result<RocketModel>
}