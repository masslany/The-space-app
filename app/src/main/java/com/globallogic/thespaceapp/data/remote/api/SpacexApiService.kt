package com.globallogic.thespaceapp.data.remote.api

import com.globallogic.thespaceapp.data.remote.response.RoadsterResponse
import com.globallogic.thespaceapp.data.remote.response.dragons.DragonsResponse
import com.globallogic.thespaceapp.data.remote.response.dragons.DragonsResponseItem
import com.globallogic.thespaceapp.data.remote.response.rockets.RocketsResponse
import com.globallogic.thespaceapp.data.remote.response.rockets.RocketsResponseItem
import com.globallogic.thespaceapp.data.remote.response.starlinks.StarlinkResponse
import com.globallogic.thespaceapp.data.remote.response.upcominglaunches.UpcomingLaunchesResponse
import com.globallogic.thespaceapp.data.remote.response.upcominglaunches.UpcomingLaunchesResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface SpacexApiService {
    @GET("/v4/roadster")
    suspend fun fetchRoadsterData(): RoadsterResponse

    @GET("/v4/launches/upcoming")
    suspend fun fetchUpcomingLaunchesData(): UpcomingLaunchesResponse

    @GET("/v4/launches/{id}")
    suspend fun fetchLaunchById(@Path("id") id: String): UpcomingLaunchesResponseItem

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
