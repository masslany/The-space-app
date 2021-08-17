package com.globallogic.thespaceapp.di

import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import com.globallogic.thespaceapp.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
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

    @ViewModelScoped
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

    @ViewModelScoped
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

    @ViewModelScoped
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

    @ViewModelScoped
    @Provides
    fun provideStarlinksRepository(
        apiService: SpacexApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): StarlinkRepository {
        return StarlinksRepositoryImpl(
            apiService,
            ioDispatcher
        )
    }
}