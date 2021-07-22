package com.globallogic.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class Diameter(
    @SerializedName("feet")
    val feet: Int,
    @SerializedName("meters")
    val meters: Int
)