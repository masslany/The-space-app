package com.masslany.thespaceapp.data.local.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masslany.thespaceapp.domain.model.StarlinkModel

@Entity(tableName = "starlinks")
data class StarlinkEntity(
    @PrimaryKey val id: String,
    val objectName: String,
    val launchDate: String,
    val TLELine0: String,
    val TLELine1: String,
    val TLELine2: String,
    val updatedAt: Long = System.currentTimeMillis()
)

fun toStarlinkModel(starlinkEntity: StarlinkEntity): StarlinkModel = StarlinkModel(
    id = starlinkEntity.id,
    objectName = starlinkEntity.objectName,
    launchDate = starlinkEntity.launchDate,
    TLELine0 = starlinkEntity.TLELine0,
    TLELine1 = starlinkEntity.TLELine1,
    TLELine2 = starlinkEntity.TLELine2
)

fun toStarlinkEntity(starlinkModel: StarlinkModel): StarlinkEntity = StarlinkEntity(
    id = starlinkModel.id,
    objectName = starlinkModel.objectName,
    launchDate = starlinkModel.launchDate,
    TLELine0 = starlinkModel.TLELine0,
    TLELine1 = starlinkModel.TLELine1,
    TLELine2 = starlinkModel.TLELine2
)

fun toStarlinkModel(starlinkEntities: List<StarlinkEntity>): List<StarlinkModel> {
    return starlinkEntities.map { entity ->
        toStarlinkModel(entity)
    }
}

fun toStarlinkEntity(starlinkModels: List<StarlinkModel>): List<StarlinkEntity> {
    return starlinkModels.map { model ->
        toStarlinkEntity(model)
    }
}