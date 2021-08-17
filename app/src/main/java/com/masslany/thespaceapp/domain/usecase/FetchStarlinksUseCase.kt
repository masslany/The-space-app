package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.StarlinkEntity
import com.masslany.thespaceapp.domain.repository.StarlinkRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchStarlinksUseCase @Inject constructor(
    private val starlinksRepository: StarlinkRepository
) {
    suspend fun execute(): Result<List<StarlinkEntity>> {
        return starlinksRepository.fetchStarlinksData()
    }
}