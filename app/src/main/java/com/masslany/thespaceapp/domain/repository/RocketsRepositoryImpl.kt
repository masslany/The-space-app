package com.masslany.thespaceapp.domain.repository

import android.net.Uri
import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.di.IoDispatcher
import com.masslany.thespaceapp.domain.model.RocketEntity
import com.masslany.thespaceapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RocketsRepositoryImp @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RocketsRepository {
    override suspend fun fetchRocketsData(): Result<List<RocketEntity>> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchRocketsData()
                val rockets = response.map { data ->
                    RocketEntity(
                        active = data.active,
                        boosters = data.boosters,
                        company = data.company,
                        costPerLaunch = data.costPerLaunch,
                        country = data.country,
                        description = data.description,
                        diameter = data.diameter,
                        engines = data.engines,
                        firstFlight = data.firstFlight,
                        firstStage = data.firstStage,
                        flickrImages = data.flickrImages,
                        height = data.height,
                        id = data.id,
                        landingLegs = data.landingLegs,
                        mass = data.mass,
                        name = data.name,
                        payloadWeights = data.payloadWeights,
                        secondStage = data.secondStage,
                        stages = data.stages,
                        successRatePct = data.successRatePct,
                        type = data.type,
                        wikipedia = Uri.parse(data.wikipedia)
                    )
                }
                Result.Success(rockets)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }

    override suspend fun fetchRocketById(id: String): Result<RocketEntity> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchRocketById(id)
                val rocket = RocketEntity(
                    active = response.active,
                    boosters = response.boosters,
                    company = response.company,
                    costPerLaunch = response.costPerLaunch,
                    country = response.country,
                    description = response.description,
                    diameter = response.diameter,
                    engines = response.engines,
                    firstFlight = response.firstFlight,
                    firstStage = response.firstStage,
                    flickrImages = response.flickrImages,
                    height = response.height,
                    id = response.id,
                    landingLegs = response.landingLegs,
                    mass = response.mass,
                    name = response.name,
                    payloadWeights = response.payloadWeights,
                    secondStage = response.secondStage,
                    stages = response.stages,
                    successRatePct = response.successRatePct,
                    type = response.type,
                    wikipedia = Uri.parse(response.wikipedia)
                )

                Result.Success(rocket)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}