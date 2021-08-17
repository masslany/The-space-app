package com.masslany.thespaceapp.domain.repository

import com.masslany.thespaceapp.domain.model.StarlinkEntity
import com.masslany.thespaceapp.utils.Result

interface StarlinkRepository {
    suspend fun fetchStarlinksData(): Result<List<StarlinkEntity>>
}