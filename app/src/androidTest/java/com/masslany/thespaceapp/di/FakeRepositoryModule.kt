package com.masslany.thespaceapp.di

import com.masslany.thespaceapp.data.local.cache.CacheDatabase
import com.masslany.thespaceapp.data.remote.api.SpacexApiService
import com.masslany.thespaceapp.domain.repository.*
import com.masslany.thespaceapp.fakes.FakeLaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {
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
    fun provideLaunchesRepository(): LaunchesRepository {
        return FakeLaunchesRepository()
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
        cacheDatabase: CacheDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): StarlinkRepository {
        return StarlinksRepositoryImpl(
            apiService,
            cacheDatabase,
            ioDispatcher
        )
    }
}