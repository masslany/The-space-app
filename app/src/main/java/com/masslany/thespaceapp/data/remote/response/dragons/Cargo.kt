package com.masslany.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class Cargo(
    @SerializedName("solar_array")
    val solarArray: Int,
    @SerializedName("unpressurized_cargo")
    val unpressurizedCargo: Boolean
)