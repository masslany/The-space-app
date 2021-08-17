package com.masslany.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class Payloads(
    @SerializedName("composite_fairing")
    val compositeFairing: CompositeFairing,
    @SerializedName("option_1")
    val option1: String
)