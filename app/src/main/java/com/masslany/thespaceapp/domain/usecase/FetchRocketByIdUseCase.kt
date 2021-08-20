package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.domain.repository.RocketsRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchRocketByIdUseCase @Inject constructor(
    private val rocketsRepository: RocketsRepository
) {
    suspend fun execute(id: String): Result<RocketModel> {
        return rocketsRepository.fetchRocketById(id)
    }
}