package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.di.IoDispatcher
import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StarlinksRepositoryImpl @Inject constructor(
    private val apiService: SpacexApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : StarlinkRepository {
    override suspend fun fetchStarlinksData(): Resource<List<StarlinkModel>> {
        return withContext(ioDispatcher) {
            try {
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
                Resource.Success(starlinks)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }
}