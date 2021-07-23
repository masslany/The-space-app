package com.globallogic.thespaceapp.di

import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.domain.repository.*
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

    @Singleton
    @Provides
    fun provideRocketsRepository(
        apiService: SpacexApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): RocketsRepository {
        return RocketsRepositoryImp(
            apiService,
            ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDragonsRepository(
        apiService: SpacexApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DragonsRepository {
        return DragonsRepositoryImpl(
            apiService,
            ioDispatcher
        )
    }
}