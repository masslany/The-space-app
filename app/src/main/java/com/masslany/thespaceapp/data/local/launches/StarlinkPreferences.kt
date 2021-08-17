package com.masslany.thespaceapp.data.local.launches

import com.masslany.thespaceapp.domain.model.CirclePreferencesModel
import kotlinx.coroutines.flow.Flow

interface StarlinkPreferences {

    fun getPreferences(): Flow<CirclePreferencesModel>

    suspend fun updatePreferences(preferences: CirclePreferencesModel)
}