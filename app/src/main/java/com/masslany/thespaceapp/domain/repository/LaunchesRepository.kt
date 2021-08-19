package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.utils.Resource
import com.masslany.thespaceapp.utils.Result

interface LaunchesRepository {
    suspend fun fetchLaunchesData(): Resource<List<LaunchModel>>

    suspend fun fetchLaunchById(id: String): Result<LaunchModel>
}