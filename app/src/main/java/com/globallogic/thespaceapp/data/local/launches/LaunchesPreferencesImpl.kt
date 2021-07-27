package com.globallogic.thespaceapp.data.local.launches

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LaunchesPreferencesImpl @Inject constructor(
    private val context: Context
) : LaunchesPreferences {
    companion object {
        //
    }

    private val Context.dataStore by preferencesDataStore(
        name = "launchesPreferences"
    )

    override fun isFavourite(id: String): Flow<Boolean> {
        val key = booleanPreferencesKey(id)
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { it[key] ?: false }
    }

    override fun setFavourite(id: String, state: Boolean) {
        TODO("Not yet implemented")
    }
}