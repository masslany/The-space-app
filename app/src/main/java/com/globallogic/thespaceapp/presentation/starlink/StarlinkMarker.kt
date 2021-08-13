package com.globallogic.thespaceapp.presentation.starlink

import com.google.android.gms.maps.model.LatLng

data class StarlinkMarker(
    val latLong: LatLng,
    val id: String,
    val objectName: String,
    val launchDate: String,
    val radius: Double = 500000.0,
    val showCoverage: Boolean = false
)
