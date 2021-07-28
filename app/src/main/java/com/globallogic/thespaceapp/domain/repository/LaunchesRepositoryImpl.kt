package com.globallogic.thespaceapp.domain.repository

import androidx.core.net.toUri
import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.di.IoDispatcher
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LaunchesRepository {
    override suspend fun fetchUpcomingLaunchesData(): Result<List<LaunchEntity>> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchUpcomingLaunchesData()
                val launches: List<LaunchEntity> = response
                    .map {
                        LaunchEntity(
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

                Result.Success(launches)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }

    override suspend fun fetchLaunchById(id: String): Result<LaunchEntity> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchLaunchById(id)
                val launch = LaunchEntity(
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

                Result.Success(launch)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}