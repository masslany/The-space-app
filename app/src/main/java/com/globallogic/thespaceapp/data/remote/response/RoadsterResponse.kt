package com.globallogic.thespaceapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class RoadsterResponse(
    @SerializedName("apoapsis_au")
    val apoapsisAu: Double,
    @SerializedName("details")
    val details: String,
    @SerializedName("earth_distance_km")
    val earthDistanceKm: Double,
    @SerializedName("earth_distance_mi")
    val earthDistanceMi: Double,
    @SerializedName("eccentricity")
    val eccentricity: Double,
    @SerializedName("epoch_jd")
    val epochJd: Double,
    @SerializedName("flickr_images")
    val flickrImages: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("inclination")
    val inclination: Double,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Int,
    @SerializedName("launch_date_utc")
    val launchDateUtc: String,
    @SerializedName("launch_mass_kg")
    val launchMassKg: Int,
    @SerializedName("launch_mass_lbs")
    val launchMassLbs: Int,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("mars_distance_km")
    val marsDistanceKm: Double,
    @SerializedName("mars_distance_mi")
    val marsDistanceMi: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("norad_id")
    val noradId: Int,
    @SerializedName("orbit_type")
    val orbitType: String,
    @SerializedName("periapsis_arg")
    val periapsisArg: Double,
    @SerializedName("periapsis_au")
    val periapsisAu: Double,
    @SerializedName("period_days")
    val periodDays: Double,
    @SerializedName("semi_major_axis_au")
    val semiMajorAxisAu: Double,
    @SerializedName("speed_kph")
    val speedKph: Double,
    @SerializedName("speed_mph")
    val speedMph: Double,
    @SerializedName("video")
    val video: String,
    @SerializedName("wikipedia")
    val wikipedia: String
)