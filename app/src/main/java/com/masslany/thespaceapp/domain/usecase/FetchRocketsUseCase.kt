package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.RocketModel
import com.masslany.thespaceapp.domain.repository.RocketsRepository
import com.masslany.thespaceapp.utils.Resource
import javax.inject.Inject

class FetchRocketsUseCase @Inject constructor(
    private val rocketsRepository: RocketsRepository
) {
    suspend fun execute(): Resource<List<RocketModel>> {
        return rocketsRepository.fetchRocketsData()
    }
}