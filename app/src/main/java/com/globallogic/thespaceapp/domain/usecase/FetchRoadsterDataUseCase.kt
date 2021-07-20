package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.RoadsterEntity
import com.globallogic.thespaceapp.domain.repository.RoadsterRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchRoadsterDataUseCase @Inject constructor(
    private val roadsterRepository: RoadsterRepository
) {
    suspend fun execute(): Result<RoadsterEntity> {
        return roadsterRepository.fetchRoadsterData()
    }
}