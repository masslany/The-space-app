package com.globallogic.thespaceapp.domain.model

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.response.upcominglaunches.Core

data class LaunchesEntity(
    val name: String,
    val details: String?,
    val date: String,
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