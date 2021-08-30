package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.DragonModel
import com.masslany.thespaceapp.utils.Resource

interface DragonsRepository {
    suspend fun fetchDragonsData(): Resource<List<DragonModel>>
    suspend fun fetchDragonById(id: String): Resource<DragonModel>
}