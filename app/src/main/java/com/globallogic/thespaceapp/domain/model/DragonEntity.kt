package com.globallogic.thespaceapp.domain.model

import android.net.Uri
import com.globallogic.thespaceapp.data.remote.response.dragons.HeatShield
import com.globallogic.thespaceapp.data.remote.response.dragons.PayloadInfo
import com.globallogic.thespaceapp.data.remote.response.dragons.Thruster

data class DragonEntity(
    val name: String,
    val active: Boolean,
    val crewCapacity: Int,
    val description: String,
    val diameter: Double,
    val dryMass: Int,
    val firstFlight: String,
    val flickrImages: List<String>,
    val id: String,
    val wikipedia: Uri,
    val heightWTrunk: Double,
    val payloadInfo: PayloadInfo,
    val heatShield: HeatShield,
    val thrusters: List<Thruster>,
)