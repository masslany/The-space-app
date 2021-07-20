package com.globallogic.thespaceapp.data.remote.api

import com.globallogic.thespaceapp.data.remote.response.RoadsterResponse
import retrofit2.http.GET

interface SpacexApiService {
    @GET("/v4/roadster")
    suspend fun fetchRoadsterData(): RoadsterResponse
}
