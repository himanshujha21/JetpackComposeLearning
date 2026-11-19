package com.example.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.model.City
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.getWeatherDescription
import com.example.weatherapp.model.getWeatherEmoji
import com.example.weatherapp.ui.theme.SkyBlueDark
import com.example.weatherapp.ui.theme.SkyBlueLight
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.roundToInt

// ============================================================
// START: CurrentWeatherCard
// The big blue card at the top of the screen.
// Shows the city name, temperature, weather and some details.
// ============================================================
@Composable
fun CurrentWeatherCard(
    city: City,
    weather: CurrentWeather,
    modifier: Modifier = Modifier,
) {
    Column(
        // Modifiers are applied in order: size -> round corners -> blue gradient -> inner space
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.verticalGradient(listOf(SkyBlueLight, SkyBlueDark)))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // ---------- City name and region ----------
        Text(
            text = city.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )
        Text(
            text = city.region,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.8f),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- Weather emoji ----------
        Text(text = getWeatherEmoji(weather.weatherCode), fontSize = 64.sp)

        // ---------- Temperature ----------
        Text(
            text = "${weather.temperature.roundToInt()}°C",
            fontSize = 64.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
        )

        // ---------- Weather description ----------
        Text(
            text = getWeatherDescription(weather.weatherCode),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ---------- Details row: feels like, humidity, wind ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            WeatherDetail(label = stringResource(R.string.feels_like), value = "${weather.feelsLike.roundToInt()}°C")
            WeatherDetail(label = stringResource(R.string.humidity), value = "${weather.humidity}%")
            WeatherDetail(label = stringResource(R.string.wind), value = "${weather.windSpeed.roundToInt()} km/h")
        }
    }
}
// ============================================================
// END: CurrentWeatherCard
// ============================================================


// ============================================================
// START: WeatherDetail
// One small value with a label under it, e.g. "87%" / "Humidity".
// Used three times in the details row of CurrentWeatherCard.
// ============================================================
@Composable
private fun WeatherDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // ---------- Value ----------
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
        )

        // ---------- Label ----------
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.8f),
        )
    }
}
// ============================================================
// END: WeatherDetail
// ============================================================


// ============================================================
// START: CurrentWeatherCardPreview
// ============================================================
@Preview
@Composable
private fun CurrentWeatherCardPreview() {
    WeatherAppTheme {
        CurrentWeatherCard(
            city = City("London", "England, United Kingdom", 51.5, -0.12),
            weather = CurrentWeather(
                temperature = 12.4,
                feelsLike = 10.1,
                humidity = 78,
                windSpeed = 14.0,
                weatherCode = 2,
            ),
        )
    }
}
// ============================================================
// END: CurrentWeatherCardPreview
// ============================================================
