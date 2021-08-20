package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.di.IoDispatcher
import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.toDateSting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class RoadsterRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RoadsterRepository {
    override suspend fun fetchRoadsterData(): Resource<RoadsterModel> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchRoadsterData()
                val entity = RoadsterModel(
                    name = response.name,
                    launchDate = response.launchDateUnix.toDateSting(),
                    speed = response.speedKph.roundToInt().toString(),
                    distanceFromEarth = response.earthDistanceKm.roundToInt().toString(),
                    distanceFromMars = response.marsDistanceKm.roundToInt().toString(),
                    description = response.details,
                    images = response.flickrImages
                )
                Resource.Success(entity)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }
}