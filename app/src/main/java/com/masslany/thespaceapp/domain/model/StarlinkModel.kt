package com.masslany.thespaceapp.domain.model

data class StarlinkModel(
    val id: String,
    val objectName: String,
    val launchDate: String,
    val TLELine0: String,
    val TLELine1: String,
    val TLELine2: String,
    val updatedAt: Long = System.currentTimeMillis()
)