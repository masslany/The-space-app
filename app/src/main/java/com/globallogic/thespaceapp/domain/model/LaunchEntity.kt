package com.globallogic.thespaceapp.domain.model

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.response.launches.Core

data class LaunchEntity(
    val id: String,
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
    val wikipedia: Uri?
)