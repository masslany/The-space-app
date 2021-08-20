package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.utils.Result

interface DragonsRepository {
    suspend fun fetchDragonsData(): Result<List<DragonModel>>
    suspend fun fetchDragonById(id: String): Result<DragonModel>
}