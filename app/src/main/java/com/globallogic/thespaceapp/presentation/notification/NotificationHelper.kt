package com.globallogic.thespaceapp.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.presentation.MainActivity

class NotificationHelper(
    private val context: Context
) {
    companion object {
        private const val LAUNCHES_CHANNEL_NAME = "Launches"
        private const val CHANNEL_ID = "launches"

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val descriptionText = "Channel dedicated to upcoming SpaceX launches"
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
        val resultIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("spaceapp://launchDetails/${tag}")
        ).apply {
            setPackage(context.packageName)
            setClass(context, MainActivity::class.java)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_ONE_SHOT
        )


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(context.getString(R.string.notification_title, name))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(tag, 0, notification)
        }
    }
}