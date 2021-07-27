package com.globallogic.thespaceapp.presentation.notification

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationSchedulerImpl @Inject constructor(
    private val workManager: WorkManager
) : NotificationScheduler {
    companion object {
        const val ONE_HOUR_SECONDS = 3600L
    }

    override fun scheduleNotification(scheduleTimeSeconds: Long, tag: String, name: String) {
        val notificationRequest: WorkRequest =
            OneTimeWorkRequestBuilder<NotificationWorker>()
//                .setInitialDelay(
//                    calculateTimeToLaunch(scheduleTimeSeconds),
//                    TimeUnit.SECONDS
//                )
                .setInitialDelay(
                    10,
                    TimeUnit.SECONDS
                )
                .addTag(name)
                .setInputData(
                    workDataOf(
                        "tag" to tag,
                        "name" to name
                    )
                )
                .build()

        workManager.enqueue(notificationRequest)
    }

    private fun calculateTimeToLaunch(launchTime: Long): Long {
        val dateNow = System.currentTimeMillis() / 1000L // convert to seconds
        return launchTime - dateNow - ONE_HOUR_SECONDS
    }
}