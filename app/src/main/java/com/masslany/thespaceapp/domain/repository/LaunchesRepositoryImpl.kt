package com.masslany.thespaceapp.domain.repository

import androidx.core.net.toUri
import androidx.room.withTransaction
import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.data.local.cache.entities.toLaunchModel
import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.di.IoDispatcher
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val database: CacheDatabase
) : LaunchesRepository {

    private val launchesDao = database.launchesDao()

    override suspend fun fetchLaunchesData(): List<LaunchModel> {
        return withContext(ioDispatcher) {
            val response = apiService.fetchLaunchesData()
            val launches: List<LaunchModel> = response
                .map {
                    LaunchModel(
                        id = it.id,
                        name = it.name,
                        details = it.details,
                        date = it.dateUnix,
                        image = it.links.patch.small,
                        crewIds = it.crew,
                        cores = it.cores,
                        rocketId = it.rocket,
                        launchpadId = it.launchpad,
                        payloadsIds = it.payloads,
                        webcast = try {
                            it.links.webcast!!.toUri()
                        } catch (e: Exception) {
                            null
                        },
                        article = try {
                            it.links.article!!.toUri()
                        } catch (e: Exception) {
                            null
                        },
                        wikipedia = try {
                            it.links.wikipedia!!.toUri()
                        } catch (e: Exception) {
                            null
                        }
                    )
                }.sortedBy { it.date }
            launches
        }
    }

    override suspend fun fetchLaunchById(id: String): Resource<LaunchModel> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchLaunchById(id)
                val launch = LaunchModel(
                    id = response.id,
                    name = response.name,
                    details = response.details,
                    date = response.dateUnix,
                    image = response.links.patch.small,
                    crewIds = response.crew,
                    cores = response.cores,
                    rocketId = response.rocket,
                    launchpadId = response.launchpad,
                    payloadsIds = response.payloads,
                    webcast = try {
                        response.links.webcast!!.toUri()
                    } catch (e: Exception) {
                        null
                    },
                    article = try {
                        response.links.article!!.toUri()
                    } catch (e: Exception) {
                        null
                    },
                    wikipedia = try {
                        response.links.wikipedia!!.toUri()
                    } catch (e: Exception) {
                        null
                    }
                )

                Resource.Success(launch)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

    override fun getCachedLaunches(): Flow<List<LaunchModel>> {

        return launchesDao.getLaunches().map {
            toLaunchModel(it)
        }
    }

    override suspend fun saveFetchedLaunches(entities: List<LaunchEntity>) {
        database.withTransaction {
            launchesDao.deleteLaunches()
            launchesDao.insertLaunches(entities)
        }
    }
}