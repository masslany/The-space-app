package com.globallogic.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class Thruster(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("fuel_1")
    val fuel1: String,
    @SerializedName("fuel_2")
    val fuel2: String,
    @SerializedName("isp")
    val isp: Int,
    @SerializedName("pods")
    val pods: Int,
    @SerializedName("thrust")
    val thrust: Thrust,
    @SerializedName("type")
    val type: String
)