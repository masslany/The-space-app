package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.preferences.StarlinkPreferences
import com.masslany.thespaceapp.domain.model.CirclePreferencesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStarlinkPreferencesUseCase @Inject constructor(
    private val starlinkPreferences: StarlinkPreferences
) {

    fun execute(): Flow<CirclePreferencesModel> {
        return starlinkPreferences.getPreferences()
    }
}