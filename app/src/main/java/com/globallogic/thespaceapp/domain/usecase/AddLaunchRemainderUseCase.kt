package com.globallogic.thespaceapp.domain.usecase

import com.globallogic.thespaceapp.data.local.launches.LaunchesPreferences
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.presentation.notification.NotificationScheduler
import javax.inject.Inject

class AddLaunchRemainderUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler,
    private val launchesPreferences: LaunchesPreferences
) {

    fun execute(launchEntity: LaunchEntity) {


        // Save state
//        launchesPreferences.isFavourite(    )


        // TODO: when false -> cancel remainder



        notificationScheduler.scheduleNotification(
            scheduleTimeSeconds = launchEntity.date,
            tag = launchEntity.id,
            name = launchEntity.name
        )
    }
}