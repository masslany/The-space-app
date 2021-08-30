package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.domain.repository.LaunchesRepository
import com.masslany.thespaceapp.utils.Resource
import javax.inject.Inject

class FetchLaunchByIdUseCase @Inject constructor(
    private val launchesRepository: LaunchesRepository
) {
    suspend fun execute(id: String): Resource<LaunchModel> {
        return launchesRepository.fetchLaunchById(id)
    }
}