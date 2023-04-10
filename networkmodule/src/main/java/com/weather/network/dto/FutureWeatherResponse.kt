package com.weather.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FutureWeatherResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezone_offset: Int,
    @SerializedName("daily") val daily: List<Daily>
) : Parcelable {
    constructor() : this(0.0, 0.0, "", 0, mutableListOf())
}

@Parcelize
data class Daily(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("weather") val weather: List<Weather>,
) : Parcelable

@Parcelize
data class Temp(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
) : Parcelable

