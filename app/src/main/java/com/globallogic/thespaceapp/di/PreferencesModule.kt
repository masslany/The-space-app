package com.globallogic.thespaceapp.di

import android.content.Context
import com.globallogic.thespaceapp.data.local.launches.LaunchesPreferences
import com.globallogic.thespaceapp.data.local.launches.LaunchesPreferencesImpl
import com.globallogic.thespaceapp.data.local.launches.StarlinkPreferences
import com.globallogic.thespaceapp.data.local.launches.StarlinkPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Singleton
    @Provides
    fun provideLaunchesPreferences(@ApplicationContext context: Context): LaunchesPreferences {
        return LaunchesPreferencesImpl(context)
    }

    @Singleton
    @Provides
    fun provideStarlinkPreferences(@ApplicationContext context: Context): StarlinkPreferences {
        return StarlinkPreferencesImpl(context)
    }
}