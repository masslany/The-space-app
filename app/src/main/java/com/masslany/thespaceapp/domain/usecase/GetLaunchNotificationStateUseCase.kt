package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.preferences.LaunchesPreferences
import com.masslany.thespaceapp.domain.model.LaunchModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLaunchNotificationStateUseCase @Inject constructor(
    private val launchesPreferences: LaunchesPreferences
) {

    fun execute(launchModel: LaunchModel): Flow<Boolean> {
        return launchesPreferences.isNotificationEnabled(launchModel.id)
    }
}