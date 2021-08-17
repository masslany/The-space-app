package com.masslany.thespaceapp.presentation.starlink

data class StarlinkMarker(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val objectName: String,
    val launchDate: String
)
