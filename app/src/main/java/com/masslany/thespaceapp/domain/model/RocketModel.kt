package com.masslany.thespaceapp.domain.model

import android.net.Uri
import com.masslany.thespaceapp.data.remote.response.rockets.*

data class RocketModel(
    val active: Boolean,
    val boosters: Int,
    val company: String,
    val costPerLaunch: Int,
    val country: String,
    val description: String,
    val diameter: Diameter,
    val engines: Engines,
    val firstFlight: String,
    val firstStage: FirstStage,
    val flickrImages: List<String>,
    val height: Height,
    val id: String,
    val landingLegs: LandingLegs,
    val mass: Mass,
    val name: String,
    val payloadWeights: List<PayloadWeight>,
    val secondStage: SecondStage,
    val stages: Int,
    val successRatePct: Int,
    val type: String,
    val wikipedia: Uri
)
