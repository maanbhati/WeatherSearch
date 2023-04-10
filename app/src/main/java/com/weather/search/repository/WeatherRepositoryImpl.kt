package com.weather.search.repository

import com.weather.search.data.local.WeatherDao
import com.weather.search.data.local.WeatherEntityModel
import com.weather.network.api.Api
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getCurrentWeather(query: String): Flow<CurrentWeatherResponse?> = flow {
        val currentWeatherResponse = api.getCurrentWeather(query)
        if (currentWeatherResponse.isSuccessful) {
            emit(currentWeatherResponse.body())
        } else {
            throw IOException()
        }
    }

    override suspend fun getFutureWeather(lat: String, long: String): Flow<FutureWeatherResponse?> =
        flow {
            val futureWeatherResponse = api.getFutureWeather(lat, long)
            if (futureWeatherResponse.isSuccessful) {
                emit(futureWeatherResponse.body())
            } else {
                throw IOException()
            }
        }

    override suspend fun getSavedWeather(): Flow<WeatherEntityModel> =
        flow {
            emitAll(weatherDao.getSavedWeather())
        }

    override suspend fun insertWeather(weatherEntityModel: WeatherEntityModel) =
        weatherDao.insert(weatherEntityModel)

    override suspend fun deleteAllWeather() = weatherDao.deleteAll()
}