package com.globallogic.thespaceapp.data.remote.response.launches


import com.google.gson.annotations.SerializedName

data class Core(
    @SerializedName("core")
    val core: String,
    @SerializedName("flight")
    val flight: Int,
    @SerializedName("gridfins")
    val gridfins: Boolean,
    @SerializedName("landing_attempt")
    val landingAttempt: Boolean,
    @SerializedName("landing_success")
    val landingSuccess: Boolean,
    @SerializedName("landing_type")
    val landingType: String,
    @SerializedName("landpad")
    val landpad: String,
    @SerializedName("legs")
    val legs: Boolean,
    @SerializedName("reused")
    val reused: Boolean
)