package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class Trunk(
    @SerializedName("cargo")
    val cargo: Cargo,
    @SerializedName("trunk_volume")
    val trunkVolume: TrunkVolume
)