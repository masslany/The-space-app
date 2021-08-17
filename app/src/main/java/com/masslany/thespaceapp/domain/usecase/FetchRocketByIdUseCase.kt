package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.RocketEntity
import com.masslany.thespaceapp.domain.repository.RocketsRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchRocketByIdUseCase @Inject constructor(
    private val rocketsRepository: RocketsRepository
) {
    suspend fun execute(id: String): Result<RocketEntity> {
        return rocketsRepository.fetchRocketById(id)
    }
}