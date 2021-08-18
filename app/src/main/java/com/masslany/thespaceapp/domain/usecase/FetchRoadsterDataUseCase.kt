package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.RoadsterModel
import com.masslany.thespaceapp.domain.repository.RoadsterRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchRoadsterDataUseCase @Inject constructor(
    private val roadsterRepository: RoadsterRepository
) {
    suspend fun execute(): Result<RoadsterModel> {
        return roadsterRepository.fetchRoadsterData()
    }
}