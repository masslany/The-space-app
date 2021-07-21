package com.globallogic.thespaceapp.di

import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.domain.repository.LaunchesRepository
import com.globallogic.thespaceapp.domain.repository.LaunchesRepositoryImpl
import com.globallogic.thespaceapp.domain.repository.RoadsterRepository
import com.globallogic.thespaceapp.domain.repository.RoadsterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRoadsterRepository(
        apiService: SpacexApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): RoadsterRepository {
        return RoadsterRepositoryImpl(
            apiService,
            ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideLaunchesRepository(
        apiService: SpacexApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(
            apiService,
            ioDispatcher
        )
    }
}