package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.domain.repository.DragonsRepository
import com.masslany.thespaceapp.utils.Result
import javax.inject.Inject

class FetchDragonByIdUseCase @Inject constructor(
    private val dragonsRepository: DragonsRepository
) {
    suspend fun execute(id: String): Result<DragonModel> {
        return dragonsRepository.fetchDragonById(id)
    }
}