package com.example.weatherapp.ui

import com.example.weatherapp.model.City
import com.example.weatherapp.model.Weather

// The weather screen is always in one of these three states.
// Because it's a sealed interface, `when` makes us handle all three.
sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

// State of the city search
data class SearchUiState(
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val errorMessage: String? = null,
)
