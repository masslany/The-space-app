package com.masslany.thespaceapp.di

import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

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
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        cacheDatabase: CacheDatabase,
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(
            apiService,
            ioDispatcher,
            cacheDatabase
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