package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class Diameter(
    @SerializedName("feet")
    val feet: Int,
    @SerializedName("meters")
    val meters: Double
)