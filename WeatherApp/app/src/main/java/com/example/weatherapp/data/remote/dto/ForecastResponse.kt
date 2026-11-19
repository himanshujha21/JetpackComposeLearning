package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// These classes match the JSON from https://api.open-meteo.com/v1/forecast
// We only list the fields we need. The others are skipped (see ignoreUnknownKeys in RetrofitClient).
// @SerialName links the JSON name (temperature_2m) to our Kotlin name (temperature).

@Serializable
data class ForecastResponse(
    val current: CurrentDto,
    val daily: DailyDto,
)

@Serializable
data class CurrentDto(
    @SerialName("temperature_2m") val temperature: Double,
    @SerialName("apparent_temperature") val feelsLike: Double,
    @SerialName("relative_humidity_2m") val humidity: Int,
    @SerialName("wind_speed_10m") val windSpeed: Double,
    @SerialName("weather_code") val weatherCode: Int,
)

@Serializable
data class DailyDto(
    val time: List<String>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val maxTemp: List<Double>,
    @SerialName("temperature_2m_min") val minTemp: List<Double>,
)
