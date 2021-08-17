package com.masslany.thespaceapp.data.local.launches

import kotlinx.coroutines.flow.Flow

interface LaunchesPreferences {

    fun isNotificationEnabled(id: String): Flow<Boolean>

    suspend fun setNotificationEnabled(id: String, enabled: Boolean)
}