package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.DragonEntity
import com.globallogic.thespaceapp.utils.Result

interface DragonsRepository {
    suspend fun fetchDragonsData(): Result<List<DragonEntity>>
    suspend fun fetchDragonById(id: String): Result<DragonEntity>
}