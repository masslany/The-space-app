package com.masslany.thespaceapp.di

import android.content.Context
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.masslany.thespaceapp.presentation.notification.NotificationScheduler
import com.masslany.thespaceapp.presentation.notification.NotificationSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNotificationScheduler(workManager: WorkManager): NotificationScheduler {
        return NotificationSchedulerImpl(workManager)
    }

}