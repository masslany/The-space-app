package com.globallogic.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class TrunkVolume(
    @SerializedName("cubic_feet")
    val cubicFeet: Int,
    @SerializedName("cubic_meters")
    val cubicMeters: Int
)