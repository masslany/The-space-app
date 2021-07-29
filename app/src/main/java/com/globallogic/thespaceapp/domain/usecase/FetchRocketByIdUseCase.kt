package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.RocketEntity
import com.globallogic.thespaceapp.domain.repository.RocketsRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchRocketByIdUseCase @Inject constructor(
    private val rocketsRepository: RocketsRepository
) {
    suspend fun execute(id: String): Result<RocketEntity> {
        return rocketsRepository.fetchRocketById(id)
    }
}