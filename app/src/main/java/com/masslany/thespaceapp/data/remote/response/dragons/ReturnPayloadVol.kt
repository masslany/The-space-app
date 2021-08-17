package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class ReturnPayloadVol(
    @SerializedName("cubic_feet")
    val cubicFeet: Int,
    @SerializedName("cubic_meters")
    val cubicMeters: Int
)