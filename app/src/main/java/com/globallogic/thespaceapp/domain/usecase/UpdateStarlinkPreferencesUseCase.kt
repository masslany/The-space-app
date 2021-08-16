package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.data.local.launches.StarlinkPreferences
import com.globallogic.thespaceapp.domain.model.CirclePreferencesModel
import javax.inject.Inject

class UpdateStarlinkPreferencesUseCase @Inject constructor(
    private val starlinkPreferences: StarlinkPreferences
) {

    suspend fun execute(preferences: CirclePreferencesModel) {
        starlinkPreferences.updatePreferences(preferences)
    }
}