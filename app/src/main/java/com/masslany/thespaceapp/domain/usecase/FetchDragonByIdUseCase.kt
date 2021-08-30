package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.domain.repository.DragonsRepository
import com.masslany.thespaceapp.utils.Resource
import javax.inject.Inject

class FetchDragonByIdUseCase @Inject constructor(
    private val dragonsRepository: DragonsRepository
) {
    suspend fun execute(id: String): Resource<DragonModel> {
        return dragonsRepository.fetchDragonById(id)
    }
}