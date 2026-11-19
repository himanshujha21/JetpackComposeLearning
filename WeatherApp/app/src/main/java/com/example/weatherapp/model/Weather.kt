package com.example.weatherapp.model

// Simple models used by the UI.
// (The classes in data/remote/dto match the JSON from the API; these match what the screen shows.)

data class City(
    val name: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
)

data class Weather(
    val city: City,
    val current: CurrentWeather,
    val forecast: List<DailyForecast>,
)

data class CurrentWeather(
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double, // km/h
    val weatherCode: Int,
)

data class DailyForecast(
    val date: String, // e.g. "2026-11-19"
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCode: Int,
)
