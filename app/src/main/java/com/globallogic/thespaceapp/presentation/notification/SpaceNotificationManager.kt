package com.globallogic.thespaceapp.presentation.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.domain.model.LaunchEntity

class SpaceNotificationManager(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "dupa"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        // Create the NotificationChannel
        val name = "MY_CHANNEL"
        val descriptionText = "AAAAAAAAA"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager =
            getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    fun createNotification(launchEntity: LaunchEntity): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Start of ${launchEntity.name} in 1 hour!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}