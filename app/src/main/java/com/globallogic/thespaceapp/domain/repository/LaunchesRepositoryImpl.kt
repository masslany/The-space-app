package com.globallogic.thespaceapp.domain.repository

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.di.IoDispatcher
import com.globallogic.thespaceapp.domain.model.LaunchesEntity
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.toDateSting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LaunchesRepository {
    override suspend fun fetchUpcomingLaunchesData(): Result<List<LaunchesEntity>> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchUpcomingLaunchesData()
                val launches: List<LaunchesEntity> = response.map {
                    LaunchesEntity(
                        name = it.name,
                        details = it.details,
                        date = it.dateUnix.toDateSting(),
                        image = it.links.patch.small,
                        crewIds = it.crew,
                        cores = it.cores,
                        rocketId = it.rocket,
                        launchpadId = it.launchpad,
                        payloadsIds = it.payloads,
                        webcast = try {
                            Uri.parse(it.links.webcast)
                        } catch (e: java.lang.Exception) {
                            null
                        },
                        article = try {
                            Uri.parse(it.links.article)
                        } catch (e: java.lang.Exception) {
                            null
                        },
                        wikipedia = try {
                            Uri.parse(it.links.wikipedia)
                        } catch (e: java.lang.Exception) {
                            null
                        }
                    )
                }

                Result.Success(launches)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}