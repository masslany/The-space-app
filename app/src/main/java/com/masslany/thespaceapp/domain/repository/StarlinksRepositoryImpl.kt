package com.masslany.thespaceapp.domain.repository

import androidx.room.withTransaction
import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import com.masslany.thespaceapp.data.local.cache.entities.StarlinkEntity
import com.masslany.thespaceapp.data.local.cache.entities.toStarlinkModel
import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.di.IoDispatcher
import com.masslany.thespaceapp.domain.model.StarlinkModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StarlinksRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    private val cacheDatabase: CacheDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : StarlinkRepository {

    private val starlinksDao = cacheDatabase.starlinkDao()

    override suspend fun fetchStarlinksData(): List<StarlinkModel> {
        return withContext(ioDispatcher) {
            val response = apiService.fetchStarlinksData()
            val starlinks = response
                .filter { data ->
                    data.latitude != null && data.longitude != null
                }
                .map { data ->
                    StarlinkModel(
                        id = data.id,
                        objectName = data.spaceTrack.oBJECTNAME,
                        launchDate = data.launch,
                        TLELine0 = data.spaceTrack.tLELINE0,
                        TLELine1 = data.spaceTrack.tLELINE1,
                        TLELine2 = data.spaceTrack.tLELINE2,
                    )
                }
            starlinks
        }
    }

    override suspend fun saveFetchedStarlinks(entities: List<StarlinkEntity>) {
        cacheDatabase.withTransaction {
            starlinksDao.deleteStarlinks()
            starlinksDao.insertStarlinks(entities)
        }
    }

    override fun getCachedStarlinks(): Flow<List<StarlinkModel>> {
        return starlinksDao.getStarlinks().map {
            toStarlinkModel(it)
        }
    }
}