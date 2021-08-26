package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.data.local.cache.entities.StarlinkEntity
import com.masslany.thespaceapp.domain.model.StarlinkModel
import kotlinx.coroutines.flow.Flow

interface StarlinkRepository {
    suspend fun fetchStarlinksData(): List<StarlinkModel>

    fun getCachedStarlinks(): Flow<List<StarlinkModel>>

    suspend fun saveFetchedStarlinks(entities: List<StarlinkEntity>)
}