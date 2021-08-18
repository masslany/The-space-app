package com.masslany.thespaceapp.domain.usecase

import com.masslany.thespaceapp.data.local.preferences.LaunchesPreferences
import com.masslany.thespaceapp.domain.model.LaunchModel
import com.masslany.thespaceapp.presentation.notification.NotificationScheduler
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleLaunchNotificationUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler,
    private val launchesPreferences: LaunchesPreferences
) {

    suspend fun execute(launchModel: LaunchModel): Boolean {
        val isEnabled = launchesPreferences.isNotificationEnabled(launchModel.id).first()
        val newState = !isEnabled
        launchesPreferences.setNotificationEnabled(launchModel.id, newState)

        when (newState) {
            true -> {
                notificationScheduler.scheduleNotification(
                    scheduleTimeSeconds = launchModel.date,
                    tag = launchModel.id,
                    name = launchModel.name
                )
            }
            false -> {
                notificationScheduler.cancelNotification(
                    tag = launchModel.id
                )
            }
        }

        return newState
    }
}