package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.domain.repository.DragonsRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchDragonByIdUseCase @Inject constructor(
    private val dragonsRepository: DragonsRepository
) {
    suspend fun execute(id: String): Result<DragonEntity> {
        return dragonsRepository.fetchDragonById(id)
    }
}