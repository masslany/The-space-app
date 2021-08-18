package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.StarlinkModel
import com.masslany.thespaceapp.utils.Resource

interface StarlinkRepository {
    suspend fun fetchStarlinksData(): Resource<List<StarlinkModel>>
}