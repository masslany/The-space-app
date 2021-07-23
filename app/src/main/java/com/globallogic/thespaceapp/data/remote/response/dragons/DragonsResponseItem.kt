package com.globallogic.thespaceapp.data.remote.response.dragons


import com.google.gson.annotations.SerializedName

data class DragonsResponseItem(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("crew_capacity")
    val crewCapacity: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("diameter")
    val diameter: Diameter,
    @SerializedName("dry_mass_kg")
    val dryMassKg: Int,
    @SerializedName("dry_mass_lb")
    val dryMassLb: Int,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("flickr_images")
    val flickrImages: List<String>,
    @SerializedName("heat_shield")
    val heatShield: HeatShield,
    @SerializedName("height_w_trunk")
    val heightWTrunk: HeightWTrunk,
    @SerializedName("id")
    val id: String,
    @SerializedName("launch_payload_mass")
    val launchPayloadMass: LaunchPayloadMass,
    @SerializedName("launch_payload_vol")
    val launchPayloadVol: LaunchPayloadVol,
    @SerializedName("name")
    val name: String,
    @SerializedName("orbit_duration_yr")
    val orbitDurationYr: Int,
    @SerializedName("pressurized_capsule")
    val pressurizedCapsule: PressurizedCapsule,
    @SerializedName("return_payload_mass")
    val returnPayloadMass: ReturnPayloadMass,
    @SerializedName("return_payload_vol")
    val returnPayloadVol: ReturnPayloadVol,
    @SerializedName("sidewall_angle_deg")
    val sidewallAngleDeg: Int,
    @SerializedName("thrusters")
    val thrusters: List<Thruster>,
    @SerializedName("trunk")
    val trunk: Trunk,
    @SerializedName("type")
    val type: String,
    @SerializedName("wikipedia")
    val wikipedia: String
)