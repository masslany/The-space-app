package com.masslany.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class Height(
    @SerializedName("feet")
    val feet: Double,
    @SerializedName("meters")
    val meters: Double
)