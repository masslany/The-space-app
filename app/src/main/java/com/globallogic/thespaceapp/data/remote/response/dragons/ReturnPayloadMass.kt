package com.globallogic.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class ReturnPayloadMass(
    @SerializedName("kg")
    val kg: Int,
    @SerializedName("lb")
    val lb: Int
)