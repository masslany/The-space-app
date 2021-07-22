package com.globallogic.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class SecondStage(
    @SerializedName("burn_time_sec")
    val burnTimeSec: Any,
    @SerializedName("engines")
    val engines: Int,
    @SerializedName("fuel_amount_tons")
    val fuelAmountTons: Int,
    @SerializedName("payloads")
    val payloads: Payloads,
    @SerializedName("reusable")
    val reusable: Boolean,
    @SerializedName("thrust")
    val thrust: Thrust
)