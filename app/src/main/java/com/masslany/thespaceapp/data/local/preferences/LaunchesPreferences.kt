package com.masslany.thespaceapp.data.local.preferences

import kotlinx.coroutines.flow.Flow

interface LaunchesPreferences {

    fun isNotificationEnabled(id: String): Flow<Boolean>

    suspend fun setNotificationEnabled(id: String, enabled: Boolean)
}