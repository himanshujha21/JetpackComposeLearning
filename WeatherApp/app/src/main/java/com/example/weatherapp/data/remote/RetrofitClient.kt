package com.example.weatherapp.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

// One Retrofit object for the whole app.
object RetrofitClient {

    private const val BASE_URL = "https://api.open-meteo.com/"

    // ignoreUnknownKeys = true means: if the JSON has fields we didn't add to our classes, skip them
    private val json = Json { ignoreUnknownKeys = true }

    // `by lazy` creates it the first time it's used
    val api: OpenMeteoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(OpenMeteoApi::class.java)
    }
}
