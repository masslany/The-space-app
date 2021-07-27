package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.presentation.notification.NotificationScheduler
import javax.inject.Inject

class AddLaunchRemainderUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {

    fun execute(launchEntity: LaunchEntity) {
        notificationScheduler.scheduleNotification(
            scheduleTimeSeconds = launchEntity.date,
            tag = launchEntity.id,
            name = launchEntity.name
        )
    }
}