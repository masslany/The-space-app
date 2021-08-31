package com.masslany.thespaceapp.di

import android.content.Context
import com.masslany.thespaceapp.data.local.preferences.LaunchesPreferences
import com.masslany.thespaceapp.data.local.preferences.LaunchesPreferencesImpl
import com.masslany.thespaceapp.data.local.preferences.StarlinkPreferences
import com.masslany.thespaceapp.data.local.preferences.StarlinkPreferencesImpl
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