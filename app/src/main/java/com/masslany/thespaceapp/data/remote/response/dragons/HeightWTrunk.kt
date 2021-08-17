package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class HeightWTrunk(
    @SerializedName("feet")
    val feet: Double,
    @SerializedName("meters")
    val meters: Double
)