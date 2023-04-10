package com.weather.search.di

import android.app.Application
import androidx.room.Room
import com.weather.search.data.local.WeatherDao
import com.weather.search.data.local.WeatherDatabase
import com.weather.search.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        callback: WeatherDatabase.Callback
    ): WeatherDatabase {
        return Room.databaseBuilder(application, WeatherDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDao {
        return db.getWeatherDao()
    }
}