package com.globallogic.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class Isp(
    @SerializedName("sea_level")
    val seaLevel: Double,
    @SerializedName("vacuum")
    val vacuum: Double
)