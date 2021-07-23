package com.globallogic.thespaceapp.domain.repository

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
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
                        thrusters = it.thrusters
                    )
                }

                Result.Success(dragons)
            } catch (e: Exception) {
                Result.Error<Any>(e)
            }
        }
    }
}