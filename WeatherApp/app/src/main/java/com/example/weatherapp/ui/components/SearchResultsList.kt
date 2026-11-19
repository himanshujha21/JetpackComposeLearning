package com.example.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.City
import com.example.weatherapp.ui.theme.WeatherAppTheme

// ============================================================
// START: SearchResultsList
// List of cities found by the search. Tapping one shows its weather.
// LazyColumn is a scrolling list that only draws the visible rows.
// ============================================================
@Composable
fun SearchResultsList(
    cities: List<City>,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        // One CityItem + a divider line for every city in the list
        items(cities) { city ->
            CityItem(city = city, onClick = { onCityClick(city) })
            HorizontalDivider()
        }
    }
}
// ============================================================
// END: SearchResultsList
// ============================================================


// ============================================================
// START: CityItem
// One row in the list: the city name with its region below it.
// The whole row is clickable.
// ============================================================
@Composable
private fun CityItem(city: City, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        // ---------- City name ----------
        Text(text = city.name, style = MaterialTheme.typography.titleMedium)

        // ---------- Region and country ----------
        Text(
            text = city.region,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
// ============================================================
// END: CityItem
// ============================================================


// ============================================================
// START: SearchResultsListPreview
// ============================================================
@Preview(showBackground = true)
@Composable
private fun SearchResultsListPreview() {
    WeatherAppTheme {
        SearchResultsList(
            cities = listOf(
                City("Paris", "Île-de-France, France", 48.85, 2.35),
                City("Paris", "Texas, United States", 33.66, -95.55),
            ),
            onCityClick = {},
        )
    }
}
// ============================================================
// END: SearchResultsListPreview
// ============================================================
