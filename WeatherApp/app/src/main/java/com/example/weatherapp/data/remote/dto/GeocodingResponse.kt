package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

// JSON from the city search API: https://geocoding-api.open-meteo.com/v1/search
// If no city is found, "results" is missing from the JSON, so we use an empty list by default.

@Serializable
data class GeocodingResponse(
    val results: List<CityDto> = emptyList(),
)

@Serializable
data class CityDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    val admin1: String? = null, // state or region, e.g. "Maharashtra"
)
