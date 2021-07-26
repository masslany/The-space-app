package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.StarlinkEntity
import com.globallogic.thespaceapp.domain.repository.StarlinkRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchStarlinksUseCase @Inject constructor(
    private val starlinksRepository: StarlinkRepository
) {
    suspend fun execute(): Result<List<StarlinkEntity>> {
        return starlinksRepository.fetchStarlinksData()
    }
}