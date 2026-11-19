package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.model.DailyForecast
import com.example.weatherapp.model.getWeatherDescription
import com.example.weatherapp.model.getWeatherEmoji
import com.example.weatherapp.ui.theme.WeatherAppTheme
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

// ============================================================
// START: DailyForecastRow
// One row of the forecast list:
//   [Day]  [emoji]  [weather text .........]  [min°]  [max°]
// The weather text uses weight(1f), so it takes all the space left over.
// ============================================================
@Composable
fun DailyForecastRow(
    dayName: String,
    forecast: DailyForecast,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ---------- Day name ----------
        Text(
            text = dayName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(64.dp),
        )

        // ---------- Weather emoji ----------
        Text(text = getWeatherEmoji(forecast.weatherCode), fontSize = 24.sp)

        // ---------- Weather description ----------
        Text(
            text = getWeatherDescription(forecast.weatherCode),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
        )

        // ---------- Lowest temperature ----------
        Text(
            text = "${forecast.minTemp.roundToInt()}°",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.width(40.dp),
        )

        // ---------- Highest temperature ----------
        Text(
            text = "${forecast.maxTemp.roundToInt()}°",
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier.width(40.dp),
        )
    }
}
// ============================================================
// END: DailyForecastRow
// ============================================================


// ============================================================
// START: formatDayName (helper function, not a Composable)
// Changes "2026-11-20" into a short day name like "Fri".
// ============================================================
fun formatDayName(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val outputFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val parsedDate = inputFormat.parse(date) ?: return date
    return outputFormat.format(parsedDate)
}
// ============================================================
// END: formatDayName
// ============================================================


// ============================================================
// START: DailyForecastRowPreview
// ============================================================
@Preview(showBackground = true)
@Composable
private fun DailyForecastRowPreview() {
    WeatherAppTheme {
        DailyForecastRow(
            dayName = "Fri",
            forecast = DailyForecast("2026-11-20", maxTemp = 14.2, minTemp = 6.8, weatherCode = 61),
        )
    }
}
// ============================================================
// END: DailyForecastRowPreview
// ============================================================
