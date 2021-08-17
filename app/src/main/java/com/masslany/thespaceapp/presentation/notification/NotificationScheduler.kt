package com.masslany.thespaceapp.presentation.notification

interface NotificationScheduler {
    fun scheduleNotification(scheduleTimeSeconds: Long, tag: String, name: String)

    fun cancelNotification(tag: String)
}