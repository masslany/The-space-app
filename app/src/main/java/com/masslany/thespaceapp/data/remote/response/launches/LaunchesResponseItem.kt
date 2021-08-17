package com.masslany.thespaceapp.data.remote.response.launches


import com.google.gson.annotations.SerializedName

data class LaunchesResponseItem(
    // Non-null:
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("upcoming")
    val upcoming: Boolean,
    @SerializedName("date_local")
    val dateLocal: String,
    @SerializedName("date_precision")
    val datePrecision: String,
    @SerializedName("date_unix")
    val dateUnix: Long,
    @SerializedName("date_utc")
    val dateUtc: String,
    @SerializedName("auto_update")
    val autoUpdate: Boolean,
    @SerializedName("net")
    val net: Boolean,
    @SerializedName("tbd")
    val tbd: Boolean,

    // Nullable:
    @SerializedName("capsules")
    val capsules: List<String>,
    @SerializedName("cores")
    val cores: List<Core>,
    @SerializedName("crew")
    val crew: List<String>,
    @SerializedName("details")
    val details: String?,
    @SerializedName("failures")
    val failures: List<Failure>,
    @SerializedName("fairings")
    val fairings: Fairings,
    @SerializedName("launch_library_id")
    val launchLibraryId: String?,
    @SerializedName("launchpad")
    val launchpad: String?,
    @SerializedName("links")
    val links: Links,
    @SerializedName("payloads")
    val payloads: List<String>,
    @SerializedName("rocket")
    val rocket: String?,
    @SerializedName("ships")
    val ships: List<String>,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: String?,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("window")
    val window: Int?
)