package com.weather.search.data.helper

import com.weather.search.data.local.DailyEntityModel
import com.weather.search.data.local.WeatherEntityModel
import com.weather.search.data.remote.DailyDomainViewModel
import com.weather.search.data.remote.WeatherDomainViewModel
import com.weather.network.dto.CurrentWeatherResponse
import com.weather.network.dto.FutureWeatherResponse
import javax.inject.Inject

class ModelConverter @Inject constructor() {
    fun convertFromDomainToEntityModel(
        currentWeatherResponse: CurrentWeatherResponse,
        futureWeatherResponse: FutureWeatherResponse
    ): WeatherEntityModel {
        return WeatherEntityModel(
            currentWeatherResponse.id,
            currentWeatherResponse.dt,
            currentWeatherResponse.name,
            currentWeatherResponse.main.temp,
            currentWeatherResponse.main.temp_min,
            currentWeatherResponse.main.temp_max,
            currentWeatherResponse.weather[0].description,
            currentWeatherResponse.weather[0].icon,
            daily = futureWeatherResponse.daily.map { DailyEntityModel(it) }
        )
    }

    fun convertEntityToDomainModel(
        weatherEntityModel: WeatherEntityModel?
    ): WeatherDomainViewModel? {
        if (weatherEntityModel != null) {
            return WeatherDomainViewModel(
                weatherEntityModel.id,
                weatherEntityModel.dt,
                weatherEntityModel.name,
                weatherEntityModel.temp,
                weatherEntityModel.temp_min,
                weatherEntityModel.temp_max,
                weatherEntityModel.description,
                weatherEntityModel.icon,
                daily = weatherEntityModel.daily.map { DailyDomainViewModel(it) }
            )
        }
        return null
    }
}