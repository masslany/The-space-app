package com.masslany.thespaceapp.data.remote.api

import com.masslany.thespaceapp.data.remote.response.dragons.DragonsResponse
import com.masslany.thespaceapp.data.remote.response.dragons.DragonsResponseItem
import com.masslany.thespaceapp.data.remote.response.launches.LaunchesResponse
import com.masslany.thespaceapp.data.remote.response.launches.LaunchesResponseItem
import com.masslany.thespaceapp.data.remote.response.roadster.RoadsterResponse
import com.masslany.thespaceapp.data.remote.response.rockets.RocketsResponse
import com.masslany.thespaceapp.data.remote.response.rockets.RocketsResponseItem
import com.masslany.thespaceapp.data.remote.response.starlinks.StarlinkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SpacexApiService {
    @GET("/v4/roadster")
    suspend fun fetchRoadsterData(): RoadsterResponse

    @GET("/v4/launches")
    suspend fun fetchLaunchesData(): LaunchesResponse

    @GET("/v4/launches/{id}")
    suspend fun fetchLaunchById(@Path("id") id: String): LaunchesResponseItem

    @GET("/v4/rockets")
    suspend fun fetchRocketsData(): RocketsResponse

    @GET("/v4/rockets/{id}")
    suspend fun fetchRocketById(@Path("id") id: String): RocketsResponseItem

    @GET("/v4/dragons")
    suspend fun fetchDragonsData(): DragonsResponse

    @GET("/v4/dragons/{id}")
    suspend fun fetchDragonById(@Path("id") id: String): DragonsResponseItem

    @GET("/v4/starlink")
    suspend fun fetchStarlinksData(): StarlinkResponse
}
