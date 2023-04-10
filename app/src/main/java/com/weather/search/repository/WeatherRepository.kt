package com.weather.search.repository

import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import com.weather.search.data.local.WeatherEntityModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(query: String): Flow<CurrentWeatherResponse?>
    suspend fun getFutureWeather(lat: String, long: String): Flow<FutureWeatherResponse?>
    suspend fun getSavedWeather(): Flow<WeatherEntityModel>
    suspend fun insertWeather(weatherEntityModel: WeatherEntityModel): Long
    suspend fun deleteAllWeather()
}