package com.globallogic.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class PayloadWeight(
    @SerializedName("id")
    val id: String,
    @SerializedName("kg")
    val kg: Int,
    @SerializedName("lb")
    val lb: Int,
    @SerializedName("name")
    val name: String
)