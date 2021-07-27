package com.globallogic.thespaceapp.presentation.notification

interface NotificationScheduler {
    fun scheduleNotification(scheduleTimeSeconds: Long, tag: String, name: String)
}