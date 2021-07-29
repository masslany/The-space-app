package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.domain.repository.LaunchesRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchUpcomingLaunchesDataUseCase @Inject constructor(
    private val launchesRepository: LaunchesRepository
) {
    suspend fun execute(): Result<List<LaunchEntity>> {
        return launchesRepository.fetchUpcomingLaunchesData()
    }
}