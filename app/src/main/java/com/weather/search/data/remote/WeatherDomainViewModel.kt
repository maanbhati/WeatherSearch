package com.weather.search.data.remote

import com.weather.search.data.local.DailyEntityModel
import com.weather.search.data.local.TempEntityModel
import com.weather.search.data.local.WeatherEntity
import kotlinx.parcelize.RawValue

data class WeatherDomainViewModel(
    val id: Int,
    val dt: Long,
    val name: String,
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val description: String,
    val icon: String,
    val daily: @RawValue List<DailyDomainViewModel>
)

data class DailyDomainViewModel(
    val dt: Long,
    val temp: TempDomainViewModel,
    val weather: @RawValue List<WeatherDomain>,
) {
    constructor(dailyEntityModel: DailyEntityModel) : this(
        dt = dailyEntityModel.dt,
        temp = TempDomainViewModel(dailyEntityModel.temp),
        weather = dailyEntityModel.weather.map { WeatherDomain(it) }
    )
}

data class TempDomainViewModel(
    val day: Double,
    val min: Double,
    val max: Double,
) {
    constructor(temp: TempEntityModel) : this(
        day = temp.day,
        min = temp.min,
        max = temp.max
    )
}

data class WeatherDomain(
    val description: String,
    val icon: String
) {
    constructor(weather: WeatherEntity) : this(
        description = weather.description,
        icon = weather.icon
    )
}
