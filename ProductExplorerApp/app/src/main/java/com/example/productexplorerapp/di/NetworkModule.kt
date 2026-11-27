package com.example.productexplorerapp.di

import com.example.productexplorerapp.data.remote.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

// LAYER: DI
// Why: Retrofit is built once, here. Every class that needs ProductApi gets
// the same ready-made object, and nobody calls Retrofit.Builder() on their own.

// A Hilt module tells Hilt how to create objects it can't create on its own.
// Retrofit is not our code, so we can't put @Inject on it.
// Instead we write "@Provides" functions that build these objects.
//
// @InstallIn(SingletonComponent::class) = these objects live as long as the app.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://fakestoreapi.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // ignoreUnknownKeys = true: if the JSON has fields we don't have in ProductDto, skip them
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // Hilt sees this needs a Retrofit and passes the one from provideRetrofit()
    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }
}
