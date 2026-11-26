package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Holds all the screen data. It survives screen rotation, so we don't reload on every rotate.
// The UI reads the StateFlows below and calls the functions when the user does something.
class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    // The weather part of the screen: Loading, Success or Error
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState.asStateFlow()

    // True while the user is doing pull-to-refresh
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // The city search results
    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    // A short message to show in a Snackbar, e.g. when refresh fails
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    // We start with a fixed city. The user can change it with the search.
    private var selectedCity = City(
        name = "London",
        region = "England, United Kingdom",
        latitude = 51.5085,
        longitude = -0.1257,
    )

    // Loading the weather is also a side effect (it calls the internet).
    // We start it here in the ViewModel's init instead of inside a Composable,
    // so it runs only once - not again when the screen rotates or recomposes.
    init {
        loadWeather()
    }

    // Loads the weather for the selected city and shows a full-screen loader meanwhile
    fun loadWeather() {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                val weather = repository.getWeather(selectedCity)
                _weatherState.value = WeatherUiState.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error(getErrorMessage(e))
            }
        }
    }

    // Pull-to-refresh: keep the old weather on screen while the new one loads
    fun refreshWeather() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val weather = repository.getWeather(selectedCity)
                _weatherState.value = WeatherUiState.Success(weather)
            } catch (e: Exception) {
                _snackbarMessage.value = "Couldn't refresh. ${getErrorMessage(e)}"
            }
            _isRefreshing.value = false
        }
    }

    fun searchCity(name: String) {
        if (name.isBlank()) return

        viewModelScope.launch {
            _searchState.value = SearchUiState(isLoading = true)
            try {
                val cities = repository.searchCity(name.trim())
                _searchState.value = if (cities.isEmpty()) {
                    SearchUiState(errorMessage = "No city found with the name \"$name\".")
                } else {
                    SearchUiState(cities = cities)
                }
            } catch (e: Exception) {
                _searchState.value = SearchUiState(errorMessage = getErrorMessage(e))
            }
        }
    }

    fun selectCity(city: City) {
        selectedCity = city
        clearSearch()
        loadWeather()
    }

    fun clearSearch() {
        _searchState.value = SearchUiState()
    }

    // Called after the Snackbar is shown, so it isn't shown again
    fun snackbarShown() {
        _snackbarMessage.value = null
    }

    // Turns an exception into a message the user can understand
    private fun getErrorMessage(e: Exception): String {
        return when (e) {
            is IOException -> "No internet connection. Please check your network."
            is HttpException -> "Server error (${e.code()}). Please try again later."
            else -> "Something went wrong. Please try again."
        }
    }
}
