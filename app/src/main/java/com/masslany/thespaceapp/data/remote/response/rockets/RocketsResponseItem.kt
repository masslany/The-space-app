package com.masslany.thespaceapp.data.remote.response.rockets


import com.google.gson.annotations.SerializedName

data class RocketsResponseItem(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("boosters")
    val boosters: Int,
    @SerializedName("company")
    val company: String,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("diameter")
    val diameter: Diameter,
    @SerializedName("engines")
    val engines: Engines,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("first_stage")
    val firstStage: FirstStage,
    @SerializedName("flickr_images")
    val flickrImages: List<String>,
    @SerializedName("height")
    val height: Height,
    @SerializedName("id")
    val id: String,
    @SerializedName("landing_legs")
    val landingLegs: LandingLegs,
    @SerializedName("mass")
    val mass: Mass,
    @SerializedName("name")
    val name: String,
    @SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeight>,
    @SerializedName("second_stage")
    val secondStage: SecondStage,
    @SerializedName("stages")
    val stages: Int,
    @SerializedName("success_rate_pct")
    val successRatePct: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("wikipedia")
    val wikipedia: String
)