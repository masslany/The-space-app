package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.DragonEntity
import com.masslany.thespaceapp.utils.Result

interface DragonsRepository {
    suspend fun fetchDragonsData(): Result<List<DragonEntity>>
    suspend fun fetchDragonById(id: String): Result<DragonEntity>
}