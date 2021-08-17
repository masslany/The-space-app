package com.masslany.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class CompositeFairing(
    @SerializedName("diameter")
    val diameter: Diameter,
    @SerializedName("height")
    val height: Height
)