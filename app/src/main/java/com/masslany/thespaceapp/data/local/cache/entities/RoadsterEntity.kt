package com.masslany.thespaceapp.data.local.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masslany.thespaceapp.domain.model.RoadsterModel

@Entity(tableName = "roadster")
data class RoadsterEntity(
    @PrimaryKey val name: String,
    val launchDate: String,
    val speed: String,
    val distanceFromEarth: String,
    val distanceFromMars: String,
    val description: String,
    val images: List<String>,
    val updatedAt: Long = System.currentTimeMillis()
)

fun toRoadsterModel(roadsterEntity: RoadsterEntity): RoadsterModel = RoadsterModel(
    name = roadsterEntity.name,
    launchDate = roadsterEntity.launchDate,
    speed = roadsterEntity.speed,
    distanceFromEarth = roadsterEntity.distanceFromEarth,
    distanceFromMars = roadsterEntity.distanceFromMars,
    description = roadsterEntity.description,
    images = roadsterEntity.images,
    updatedAt = roadsterEntity.updatedAt
)

fun toRoadsterEntity(roadsterModel: RoadsterModel): RoadsterEntity = RoadsterEntity(
    name = roadsterModel.name,
    launchDate = roadsterModel.launchDate,
    speed = roadsterModel.speed,
    distanceFromEarth = roadsterModel.distanceFromEarth,
    distanceFromMars = roadsterModel.distanceFromMars,
    description = roadsterModel.description,
    images = roadsterModel.images,
    updatedAt = roadsterModel.updatedAt
)

fun toRoadsterModel(roadsterEntities: List<RoadsterEntity>): List<RoadsterModel> {
    return roadsterEntities.map { entity ->
        toRoadsterModel(entity)
    }
}

fun toRoadsterEntity(roadsterModels: List<RoadsterModel>): List<RoadsterEntity> {
    return roadsterModels.map { entity ->
        toRoadsterEntity(entity)
    }
}

