package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.preferences.StarlinkPreferences
import com.masslany.thespaceapp.domain.model.CirclePreferencesModel
import javax.inject.Inject

class UpdateStarlinkPreferencesUseCase @Inject constructor(
    private val starlinkPreferences: StarlinkPreferences
) {

    suspend fun execute(preferences: CirclePreferencesModel) {
        starlinkPreferences.updatePreferences(preferences)
    }
}