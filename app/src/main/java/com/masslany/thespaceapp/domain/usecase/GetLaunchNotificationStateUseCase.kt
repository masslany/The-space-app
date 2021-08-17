package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.launches.LaunchesPreferences
import com.masslany.thespaceapp.domain.model.LaunchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLaunchNotificationStateUseCase @Inject constructor(
    private val launchesPreferences: LaunchesPreferences
) {

    fun execute(launchEntity: LaunchEntity): Flow<Boolean> {
        return launchesPreferences.isNotificationEnabled(launchEntity.id)
    }
}