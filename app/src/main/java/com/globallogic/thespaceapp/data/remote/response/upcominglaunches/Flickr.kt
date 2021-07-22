package com.globallogic.thespaceapp.data.remote.response.upcominglaunches


import com.google.gson.annotations.SerializedName

data class Flickr(
    @SerializedName("original")
    val original: List<String>,
    @SerializedName("small")
    val small: List<String>
)