package com.masslany.thespaceapp.fakes

import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLaunchesRepository(
    private val launchesData: Resource<List<LaunchModel>>,
    private val cachedLaunches: List<LaunchModel>
) : LaunchesRepository {

    val cached = mutableListOf<LaunchEntity>()

    override suspend fun fetchLaunchesData(): Resource<List<LaunchModel>> {
        return launchesData
    }

    override suspend fun fetchLaunchById(id: String): Result<LaunchModel> {
        TODO()
    }

    override fun getCachedLaunches(): Flow<List<LaunchModel>> {
        return flowOf(cachedLaunches)
    }

    override suspend fun saveFetchedLaunches(entities: List<LaunchEntity>) {
        cached.clear()
        cached.addAll(entities)
    }
}