package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.launches.LaunchesPreferences
import com.masslany.thespaceapp.domain.model.LaunchEntity
import com.masslany.thespaceapp.presentation.notification.NotificationScheduler
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleLaunchNotificationUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler,
    private val launchesPreferences: LaunchesPreferences
) {

    suspend fun execute(launchEntity: LaunchEntity): Boolean {
        val isEnabled = launchesPreferences.isNotificationEnabled(launchEntity.id).first()
        val newState = !isEnabled
        launchesPreferences.setNotificationEnabled(launchEntity.id, newState)

        when (newState) {
            true -> {
                notificationScheduler.scheduleNotification(
                    scheduleTimeSeconds = launchEntity.date,
                    tag = launchEntity.id,
                    name = launchEntity.name
                )
            }
            false -> {
                notificationScheduler.cancelNotification(
                    tag = launchEntity.id
                )
            }
        }

        return newState
    }
}