package com.globallogic.thespaceapp.data.remote.api

import com.globallogic.thespaceapp.data.remote.response.RoadsterResponse
import com.globallogic.thespaceapp.data.remote.response.dragons.DragonsResponse
import com.globallogic.thespaceapp.data.remote.response.rockets.RocketsResponse
import com.globallogic.thespaceapp.data.remote.response.upcominglaunches.UpcomingLaunchesResponse
import retrofit2.http.GET

interface SpacexApiService {
    @GET("/v4/roadster")
    suspend fun fetchRoadsterData(): RoadsterResponse

    @GET("/v4/launches/upcoming")
    suspend fun fetchUpcomingLaunchesData(): UpcomingLaunchesResponse

    @GET("/v4/rockets")
    suspend fun fetchRocketsData(): RocketsResponse

    @GET("/v4/dragons")
    suspend fun fetchDragonsData(): DragonsResponse
}
