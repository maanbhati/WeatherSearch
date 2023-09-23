package com.weather.search.di

import com.weather.network.api.Api
import com.weather.search.core.DispatcherProviderImpl
import com.weather.search.core.DispatcherProvider
import com.weather.search.data.local.WeatherDao
import com.weather.search.repository.WeatherRepository
import com.weather.search.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(api: Api, weatherDao: WeatherDao): WeatherRepository {
        return WeatherRepositoryImpl(api, weatherDao)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}