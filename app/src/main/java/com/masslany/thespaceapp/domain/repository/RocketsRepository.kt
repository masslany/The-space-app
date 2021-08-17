package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.RocketEntity
import com.masslany.thespaceapp.utils.Result

interface RocketsRepository {
    suspend fun fetchRocketsData(): Result<List<RocketEntity>>

    suspend fun fetchRocketById(id: String): Result<RocketEntity>
}