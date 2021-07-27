package com.globallogic.thespaceapp.data.local.launches

import kotlinx.coroutines.flow.Flow

interface LaunchesPreferences {

    fun isFavourite(id: String): Flow<Boolean>

    fun setFavourite(id: String, state: Boolean)
}