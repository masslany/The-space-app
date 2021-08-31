package com.masslany.thespaceapp.di

import android.app.Application
import androidx.room.Room
import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CacheDatabase {
        return Room.databaseBuilder(app, CacheDatabase::class.java, "cache_database")
            .fallbackToDestructiveMigration()
           .build()
    }
}