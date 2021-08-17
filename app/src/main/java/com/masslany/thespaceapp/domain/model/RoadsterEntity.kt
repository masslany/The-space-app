package com.masslany.thespaceapp.domain.model

data class RoadsterEntity(
    val name: String,
    val launchDate: String,
    val speed: String,
    val distanceFromEarth: String,
    val distanceFromMars: String,
    val description: String,
    val images: List<String>,
)
