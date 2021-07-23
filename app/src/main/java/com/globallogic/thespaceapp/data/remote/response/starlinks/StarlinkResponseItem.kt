package com.globallogic.thespaceapp.data.remote.response.starlinks


import com.google.gson.annotations.SerializedName

data class StarlinkResponseItem(
    @SerializedName("height_km")
    val heightKm: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("latitude")
    val latitude: Any,
    @SerializedName("launch")
    val launch: String,
    @SerializedName("longitude")
    val longitude: Any,
    @SerializedName("spaceTrack")
    val spaceTrack: SpaceTrack,
    @SerializedName("velocity_kms")
    val velocityKms: Any,
    @SerializedName("version")
    val version: String
)