package com.globallogic.thespaceapp.domain.repository

import com.globallogic.thespaceapp.domain.model.StarlinkEntity
import com.globallogic.thespaceapp.utils.Result

interface StarlinkRepository {
    suspend fun fetchStarlinksData(): Result<List<StarlinkEntity>>
}