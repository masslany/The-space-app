package com.masslany.thespaceapp.fakes

import android.net.Uri
import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.data.remote.response.launches.Core
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLaunchesRepository : LaunchesRepository {

    val launches = listOf(
        createLaunchModel("1", "Starlink A"),
        createLaunchModel("2", "Crew 3"),
        createLaunchModel("3", "Polar 17")
    )

    override suspend fun fetchLaunchesData(): List<LaunchModel> {
        return launches
    }

    override suspend fun fetchLaunchById(id: String): Result<LaunchModel> {
        val launch = launches.find { it.id == id }
        return if (launch != null) {
            Result.Success(launch)
        } else {
            Result.Error<Exception>(NullPointerException())
        }
    }

    override fun getCachedLaunches(): Flow<List<LaunchModel>> {
        return flowOf(launches)
    }

    override suspend fun saveFetchedLaunches(entities: List<LaunchEntity>) {

    }

    private fun createLaunchModel(
        id: String = "id1",
        name: String = "Starlink 22",
        details: String? = null,
        date: Long = 31243252345,
        image: String? = null,
        rocketId: String? = null,
        launchpadId: String? = null,
        cores: List<Core> = emptyList(),
        crewIds: List<String> = emptyList(),
        payloadsIds: List<String> = emptyList(),
        webcast: Uri? = null,
        article: Uri? = null,
        wikipedia: Uri? = null,
        updatedAt: Long = 0
    ) = LaunchModel(
        id = id,
        name = name,
        details = details,
        date = date,
        image = image,
        rocketId = rocketId,
        launchpadId = launchpadId,
        cores = cores,
        crewIds = crewIds,
        payloadsIds = payloadsIds,
        webcast = webcast,
        article = article,
        wikipedia = wikipedia,
        updatedAt = updatedAt
    )
}