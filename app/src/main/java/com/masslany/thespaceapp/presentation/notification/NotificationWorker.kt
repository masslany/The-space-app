package com.masslany.thespaceapp.presentation.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val tag = inputData.getString("tag") ?: return Result.failure()
        val name = inputData.getString("name") ?: return Result.failure()

        val helper = NotificationHelper(context)
        helper.sendNotification(name, tag)

        return Result.success()
    }
}