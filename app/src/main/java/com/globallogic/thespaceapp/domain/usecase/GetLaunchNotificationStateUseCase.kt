package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.data.local.launches.LaunchesPreferences
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLaunchNotificationStateUseCase @Inject constructor(
    private val launchesPreferences: LaunchesPreferences
) {

    fun execute(launchEntity: LaunchEntity): Flow<Boolean> {
        return launchesPreferences.isNotificationEnabled(launchEntity.id)
    }
}