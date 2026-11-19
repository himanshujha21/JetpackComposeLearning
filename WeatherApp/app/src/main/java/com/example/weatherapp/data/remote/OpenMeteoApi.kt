package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.dto.ForecastResponse
import com.example.weatherapp.data.remote.dto.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit creates the real network code from this interface.
// The functions are `suspend`, so we call them from a coroutine and the UI doesn't freeze.
// Open-Meteo is free and doesn't need an API key.
interface OpenMeteoApi {

    // Example: https://api.open-meteo.com/v1/forecast?latitude=51.5&longitude=-0.12&current=temperature_2m
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "auto",
    ): ForecastResponse

    // City search is on a different website, so we write the full URL here.
    // A full URL in @GET is used instead of the base URL.
    @GET("https://geocoding-api.open-meteo.com/v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
    ): GeocodingResponse
}
