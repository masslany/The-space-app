package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class Thrust(
    @SerializedName("kN")
    val kN: Double,
    @SerializedName("lbf")
    val lbf: Int
)