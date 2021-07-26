package com.globallogic.thespaceapp.domain.repository

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.data.remote.response.dragons.PayloadInfo
import com.globallogic.thespaceapp.di.IoDispatcher
import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DragonsRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DragonsRepository {
    override suspend fun fetchDragonsData(): Result<List<DragonEntity>> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchDragonsData()
                val dragons: List<DragonEntity> = response.map {
                    DragonEntity(
                        name = it.name,
                        active = it.active,
                        crewCapacity = it.crewCapacity,
                        description = it.description,
                        diameter = it.diameter.meters,
                        dryMass = it.dryMassKg,
                        firstFlight = it.firstFlight,
                        flickrImages = it.flickrImages,
                        id = it.id,
                        wikipedia = Uri.parse(it.wikipedia),
                        heightWTrunk = it.heightWTrunk.meters,
                        payloadInfo = PayloadInfo(
                            launchMass = it.launchPayloadMass.kg,
                            launchVolume = it.launchPayloadVol.cubicMeters,
                            returnMass = it.returnPayloadMass.kg,
                            returnVolume = it.returnPayloadVol.cubicMeters
                        ),
                        heatShield = it.heatShield,
                        thrusters = it.thrusters
                    )
                }

                Result.Success(dragons)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }

    override suspend fun fetchDragonById(id: String): Result<DragonEntity> {
        return withContext(ioDispatcher) {
            try {
                val response = apiService.fetchDragonById(id)
                val dragons = DragonEntity(
                    name = response.name,
                    active = response.active,
                    crewCapacity = response.crewCapacity,
                    description = response.description,
                    diameter = response.diameter.meters,
                    dryMass = response.dryMassKg,
                    firstFlight = response.firstFlight,
                    flickrImages = response.flickrImages,
                    id = response.id,
                    wikipedia = Uri.parse(response.wikipedia),
                    heightWTrunk = response.heightWTrunk.meters,
                    payloadInfo = PayloadInfo(
                        launchMass = response.launchPayloadMass.kg,
                        launchVolume = response.launchPayloadVol.cubicMeters,
                        returnMass = response.returnPayloadMass.kg,
                        returnVolume = response.returnPayloadVol.cubicMeters
                    ),
                    heatShield = response.heatShield,
                    thrusters = response.thrusters
                )

                Result.Success(dragons)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}