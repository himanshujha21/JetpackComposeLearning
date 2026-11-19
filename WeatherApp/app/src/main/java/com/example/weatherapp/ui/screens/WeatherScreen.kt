package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.model.City
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.Weather
import com.example.weatherapp.ui.WeatherUiState
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.components.CitySearchBar
import com.example.weatherapp.ui.components.CurrentWeatherCard
import com.example.weatherapp.ui.components.DailyForecastRow
import com.example.weatherapp.ui.components.ErrorView
import com.example.weatherapp.ui.components.LoadingView
import com.example.weatherapp.ui.components.SearchResultsList
import com.example.weatherapp.ui.components.formatDayName
import com.example.weatherapp.ui.theme.WeatherAppTheme

// ============================================================
// START: WeatherScreen
// The main (and only) screen of the app.
// It reads the state from WeatherViewModel and decides what to show:
//   - the search results, while the user is searching
//   - otherwise the weather (loading, error or the forecast)
// ============================================================
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    // ---------- State from the ViewModel ----------
    // collectAsStateWithLifecycle reads a StateFlow as Compose state.
    // When the value changes, the screen redraws (recomposes) by itself.
    // It stops collecting when the app goes to the background, which saves battery.
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    // ---------- Local UI state ----------
    // The text in the search field. rememberSaveable keeps it when the screen rotates.
    var searchText by rememberSaveable { mutableStateOf("") }

    // Controls the Snackbar (the small message at the bottom of the screen)
    val snackbarHostState = remember { SnackbarHostState() }

    // ---------- Show Snackbar messages ----------
    // LaunchedEffect runs this block every time snackbarMessage changes.
    // showSnackbar is a suspend function, so it must be called from a coroutine like this one.
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.snackbarShown()
        }
    }

    // ---------- Screen layout ----------
    // Scaffold gives us a basic screen structure and a place to show the Snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ---------- Search bar ----------
            CitySearchBar(
                text = searchText,
                onTextChange = { newText ->
                    searchText = newText
                    // When the field is cleared, go back to the weather
                    if (newText.isEmpty()) {
                        viewModel.clearSearch()
                    }
                },
                onSearch = { viewModel.searchCity(searchText) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Search results OR weather ----------
            when {
                searchState.isLoading -> LoadingView()

                searchState.errorMessage != null -> ErrorView(
                    message = searchState.errorMessage.orEmpty(),
                    onRetry = { viewModel.searchCity(searchText) },
                )

                searchState.cities.isNotEmpty() -> SearchResultsList(
                    cities = searchState.cities,
                    onCityClick = { city ->
                        searchText = ""
                        viewModel.selectCity(city)
                    },
                )

                else -> WeatherContent(
                    state = weatherState,
                    isRefreshing = isRefreshing,
                    onRefresh = { viewModel.refreshWeather() },
                    onRetry = { viewModel.loadWeather() },
                )
            }
        }
    }
}
// ============================================================
// END: WeatherScreen
// ============================================================


// ============================================================
// START: WeatherContent
// Shows one of three things, depending on the WeatherUiState:
//   Loading -> a spinner
//   Error   -> an error message with a Retry button
//   Success -> the weather, inside a pull-to-refresh box
// ============================================================
@Composable
fun WeatherContent(
    state: WeatherUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
) {
    when (state) {
        is WeatherUiState.Loading -> LoadingView()

        is WeatherUiState.Error -> ErrorView(message = state.message, onRetry = onRetry)

        is WeatherUiState.Success -> {
            // PullToRefreshBox: pull the list down to call onRefresh
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize(),
            ) {
                WeatherDetails(weather = state.weather)
            }
        }
    }
}
// ============================================================
// END: WeatherContent
// ============================================================


// ============================================================
// START: WeatherDetails
// A scrolling list with the current weather card on top
// and one row for each day of the forecast below it.
// LazyColumn only draws the rows that are visible on screen.
// ============================================================
@Composable
fun WeatherDetails(weather: Weather) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        // ---------- Current weather card ----------
        item {
            CurrentWeatherCard(city = weather.city, weather = weather.current)
        }

        // ---------- Forecast title ----------
        item {
            Text(
                text = stringResource(R.string.forecast_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
            )
        }

        // ---------- One row per day ----------
        itemsIndexed(weather.forecast) { index, day ->
            // The first day in the list is today
            val dayName = if (index == 0) stringResource(R.string.today) else formatDayName(day.date)
            DailyForecastRow(dayName = dayName, forecast = day)
        }

        // ---------- Space at the bottom ----------
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
// ============================================================
// END: WeatherDetails
// ============================================================


// ============================================================
// START: WeatherDetailsPreview
// Shows WeatherDetails in Android Studio's preview with fake data,
// so we can see the design without running the app.
// ============================================================
@Preview(showBackground = true)
@Composable
private fun WeatherDetailsPreview() {
    val weather = Weather(
        city = City("London", "England, United Kingdom", 51.5, -0.12),
        current = CurrentWeather(
            temperature = 12.0,
            feelsLike = 10.0,
            humidity = 80,
            windSpeed = 15.0,
            weatherCode = 3,
        ),
        forecast = listOf(
            DailyForecast("2026-11-19", maxTemp = 13.0, minTemp = 7.0, weatherCode = 3),
            DailyForecast("2026-11-20", maxTemp = 11.0, minTemp = 5.0, weatherCode = 61),
            DailyForecast("2026-11-21", maxTemp = 9.0, minTemp = 3.0, weatherCode = 0),
        ),
    )
    WeatherAppTheme {
        WeatherDetails(weather = weather)
    }
}
// ============================================================
// END: WeatherDetailsPreview
// ============================================================
