package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.di.IoDispatcher
import com.globallogic.thespaceapp.domain.model.RoadsterEntity
import com.globallogic.thespaceapp.utils.Result
import com.globallogic.thespaceapp.utils.toDateSting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

class RoadsterRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RoadsterRepository {
    override suspend fun fetchRoadsterData(): Result<RoadsterEntity> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchRoadsterData()
                val entity = RoadsterEntity(
                    name = response.name,
                    launchDate = response.launchDateUnix.toDateSting(),
                    speed = response.speedKph.roundToInt().toString(),
                    distanceFromEarth = response.earthDistanceKm.roundToInt().toString(),
                    distanceFromMars = response.marsDistanceKm.roundToInt().toString(),
                    description = response.details,
                    image = response.flickrImages[Random.nextInt(0, 3)]
                )
                Result.Success(entity)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}