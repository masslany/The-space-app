package com.globallogic.thespaceapp.domain.model

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.response.upcominglaunches.Core

data class LaunchesEntity(
    val name: String,
    val details: String,
    val date: String,
    val image: String?,
    val crew: List<Any>,
    val cores: List<Core>,
    val rocketId: String,
    val launchpadId: String,
    val payloadsIds: List<String>,
    val webcast: Uri?,
    val article: Uri?,
    val wikipedia: Uri?
)