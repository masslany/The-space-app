package com.globallogic.thespaceapp.presentation.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

//        val notification = SpaceNotificationManager(context).createNotification()
//        notificationManager.notify(tag, 0, notification)

        return Result.success()
    }
}