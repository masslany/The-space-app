package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.data.local.launches.StarlinkPreferences
import com.globallogic.thespaceapp.domain.model.CirclePreferencesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStarlinkPreferencesUseCase @Inject constructor(
    private val starlinkPreferences: StarlinkPreferences
) {

    fun execute(): Flow<CirclePreferencesModel> {
        return starlinkPreferences.getPreferences()
    }
}