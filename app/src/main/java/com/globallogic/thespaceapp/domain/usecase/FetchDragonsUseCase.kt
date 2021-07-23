package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.domain.repository.DragonsRepository
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class FetchDragonsUseCase @Inject constructor(
    private val dragonsRepository: DragonsRepository
) {
    suspend fun execute(): Result<List<DragonEntity>> {
        return dragonsRepository.fetchDragonsData()
    }
}