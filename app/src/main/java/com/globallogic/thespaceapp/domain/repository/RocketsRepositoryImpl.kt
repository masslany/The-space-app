package com.globallogic.thespaceapp.domain.repository

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.di.IoDispatcher
import com.globallogic.thespaceapp.domain.model.RocketEntity
import com.globallogic.thespaceapp.utils.Result
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
}