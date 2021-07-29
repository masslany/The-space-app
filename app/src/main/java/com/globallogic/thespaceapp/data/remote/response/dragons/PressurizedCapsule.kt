package com.globallogic.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class PressurizedCapsule(
    @SerializedName("payload_volume")
    val payloadVolume: PayloadVolume
)