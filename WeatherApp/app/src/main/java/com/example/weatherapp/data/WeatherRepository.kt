package com.example.weatherapp.data

import com.example.weatherapp.data.remote.RetrofitClient
import com.example.weatherapp.model.City
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.Weather

// The ViewModel gets its data from here.
// The repository calls the API and turns the JSON classes into simple app models.
// Retrofit's suspend functions already run off the main thread, so we can call them directly.
class WeatherRepository {

    private val api = RetrofitClient.api

    suspend fun getWeather(city: City): Weather {
        val response = api.getForecast(city.latitude, city.longitude)

        val current = CurrentWeather(
            temperature = response.current.temperature,
            feelsLike = response.current.feelsLike,
            humidity = response.current.humidity,
            windSpeed = response.current.windSpeed,
            weatherCode = response.current.weatherCode,
        )

        // The daily data comes as separate lists. Item 0 of every list is today, item 1 is tomorrow...
        val daily = response.daily
        val forecast = daily.time.indices.map { i ->
            DailyForecast(
                date = daily.time[i],
                maxTemp = daily.maxTemp[i],
                minTemp = daily.minTemp[i],
                weatherCode = daily.weatherCode[i],
            )
        }

        return Weather(city = city, current = current, forecast = forecast)
    }

    suspend fun searchCity(name: String): List<City> {
        val response = api.searchCity(name)

        return response.results.map { result ->
            City(
                name = result.name,
                // e.g. "Maharashtra, India". Skips the parts that are missing.
                region = listOfNotNull(result.admin1, result.country).joinToString(", "),
                latitude = result.latitude,
                longitude = result.longitude,
            )
        }
    }
}
