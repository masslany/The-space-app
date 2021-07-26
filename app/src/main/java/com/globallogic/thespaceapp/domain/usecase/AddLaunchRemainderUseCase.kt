package com.globallogic.thespaceapp.domain.usecase

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.globallogic.thespaceapp.domain.model.LaunchEntity
import com.globallogic.thespaceapp.presentation.notification.NotificationWorker
import com.globallogic.thespaceapp.presentation.notification.SpaceNotificationManager
import com.globallogic.thespaceapp.utils.Result
import javax.inject.Inject

class AddLaunchRemainderUseCase @Inject constructor(
    private val context: Context,
    private val spaceNotificationManager: SpaceNotificationManager,
    private val workManager: WorkManager
) {
    suspend fun execute(launchEntity: LaunchEntity) {
        val notification = spaceNotificationManager.createNotification(launchEntity)

//        OneTimeWorkRequest.Builder().
//        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorker::class.java))
    }
}