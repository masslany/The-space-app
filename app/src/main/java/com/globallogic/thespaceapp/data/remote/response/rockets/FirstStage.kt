package com.globallogic.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class FirstStage(
    @SerializedName("burn_time_sec")
    val burnTimeSec: Double,
    @SerializedName("engines")
    val engines: Int,
    @SerializedName("fuel_amount_tons")
    val fuelAmountTons: Double,
    @SerializedName("reusable")
    val reusable: Boolean,
    @SerializedName("thrust_sea_level")
    val thrustSeaLevel: ThrustSeaLevel,
    @SerializedName("thrust_vacuum")
    val thrustVacuum: ThrustVacuum
)