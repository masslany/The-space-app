package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchUpcomingLaunchesDataUseCase @Inject constructor(
    private val launchesRepository: LaunchesRepository
) {
    suspend fun execute(): Result<List<LaunchModel>> {
        return launchesRepository.fetchUpcomingLaunchesData()
    }
}