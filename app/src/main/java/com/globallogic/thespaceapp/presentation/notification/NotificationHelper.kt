package com.globallogic.thespaceapp.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.globallogic.thespaceapp.R

class NotificationHelper(
    private val context: Context
) {
    companion object {
        private const val LAUNCHES_CHANNEL_NAME = "Launches"
        private const val CHANNEL_ID = "dupa"

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val descriptionText = "AAAAAAAAA"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(CHANNEL_ID, LAUNCHES_CHANNEL_NAME, importance)
                mChannel.description = descriptionText

                val notificationManager =
                    getSystemService(
                        context,
                        NotificationManager::class.java
                    ) as NotificationManager

                notificationManager.createNotificationChannel(mChannel)
            }
        }
    }

    fun sendNotification(name: String, tag: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Start of $name in 1 hour!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(tag, 0, notification)
        }
    }
}