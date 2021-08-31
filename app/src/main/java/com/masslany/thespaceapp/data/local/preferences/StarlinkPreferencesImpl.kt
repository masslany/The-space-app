package com.masslany.thespaceapp.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.masslany.thespaceapp.domain.model.CirclePreferencesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class StarlinkPreferencesImpl @Inject constructor(
    private val context: Context
) : StarlinkPreferences {

    companion object {
        private val showCoveragesKey = booleanPreferencesKey("showCoverage")
        private val degreesValueKey = doublePreferencesKey("degreesValue")
    }

    private val Context.starlinkDataStore by preferencesDataStore(
        name = "starlinkPreferences"
    )

    override fun getPreferences(): Flow<CirclePreferencesModel> {
        return context.starlinkDataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            CirclePreferencesModel(
                degrees = it[degreesValueKey] ?: 25.0,
                showCoverage = it[showCoveragesKey] ?: false,
            )
        }
    }

    override suspend fun updatePreferences(preferences: CirclePreferencesModel) {
        context.starlinkDataStore.edit { currentPreferences ->
            currentPreferences[showCoveragesKey] = preferences.showCoverage
            currentPreferences[degreesValueKey] = preferences.degrees
        }
    }
}