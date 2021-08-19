package com.masslany.thespaceapp.data.local.cache.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masslany.thespaceapp.data.remote.response.launches.Core
import com.masslany.thespaceapp.domain.model.LaunchModel

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val details: String?,
    val date: Long,
    val image: String?,
    val rocketId: String?,
    val launchpadId: String?,
    val cores: List<Core>,
    val crewIds: List<String>,
    val payloadsIds: List<String>,
    val webcast: Uri?,
    val article: Uri?,
    val wikipedia: Uri?,
    val updatedAt: Long = System.currentTimeMillis()
)

fun toLaunchModel(launchEntity: LaunchEntity): LaunchModel {
    return LaunchModel(
        id = launchEntity.id,
        name = launchEntity.name,
        details = launchEntity.details,
        date = launchEntity.date,
        image = launchEntity.image,
        rocketId = launchEntity.rocketId,
        launchpadId = launchEntity.launchpadId,
        cores = launchEntity.cores,
        crewIds = launchEntity.crewIds,
        payloadsIds = launchEntity.payloadsIds,
        webcast = launchEntity.webcast,
        article = launchEntity.article,
        wikipedia = launchEntity.wikipedia,
        updatedAt = launchEntity.updatedAt
    )
}

fun toLaunchEntity(launchModel: LaunchModel): LaunchEntity {
    return LaunchEntity(
        id = launchModel.id,
        name = launchModel.name,
        details = launchModel.details,
        date = launchModel.date,
        image = launchModel.image,
        rocketId = launchModel.rocketId,
        launchpadId = launchModel.launchpadId,
        cores = launchModel.cores,
        crewIds = launchModel.crewIds,
        payloadsIds = launchModel.payloadsIds,
        webcast = launchModel.webcast,
        article = launchModel.article,
        wikipedia = launchModel.wikipedia,
        updatedAt = launchModel.updatedAt
    )
}

fun toLaunchModel(launchEntities: List<LaunchEntity>): List<LaunchModel> {
    return launchEntities.map {
        toLaunchModel(it)
    }
}

fun toLaunchEntity(launchModels: List<LaunchModel>): List<LaunchEntity> {
    return launchModels.map {
        toLaunchEntity(it)
    }
}