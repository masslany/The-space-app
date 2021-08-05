package com.globallogic.thespaceapp.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TheSpaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant()
    }
}