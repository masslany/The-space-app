package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.RocketEntity
import com.globallogic.thespaceapp.utils.Result

interface RocketsRepository {
    suspend fun fetchRocketsData(): Result<List<RocketEntity>>
}