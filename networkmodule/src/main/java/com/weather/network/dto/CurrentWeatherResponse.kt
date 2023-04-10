package com.weather.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeatherResponse(
    @SerializedName("coord") val coord: Coord,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("dt") val dt: Long,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) : Parcelable {
    constructor() : this(
        Coord(0.0, 0.0),
        mutableListOf(),
        Main(0.0, 0.0, 0.0),
        0,
        0,
        ""
    )
}

@Parcelize
data class Weather(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
) : Parcelable

@Parcelize
data class Coord(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
) : Parcelable

@Parcelize
data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_min") val temp_min: Double,
    @SerializedName("temp_max") val temp_max: Double,
) : Parcelable

