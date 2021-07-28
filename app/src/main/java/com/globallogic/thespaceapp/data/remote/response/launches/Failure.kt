package com.globallogic.thespaceapp.data.remote.response.launches

import com.google.gson.annotations.SerializedName

data class Failure(
    @SerializedName("time")
    val time: Long,
    @SerializedName("altitude")
    val altitude: Long,
    @SerializedName("reason")
    val reason: String,
)