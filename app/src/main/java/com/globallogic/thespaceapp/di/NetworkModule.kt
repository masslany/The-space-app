package com.globallogic.thespaceapp.di

import com.globallogic.thespaceapp.data.remote.api.SpacexApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.spacexdata.com"
    }

    @Provides
    fun provideSpacexApi(): SpacexApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpacexApiService::class.java)
    }
}