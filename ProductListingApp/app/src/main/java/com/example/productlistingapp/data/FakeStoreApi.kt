package com.example.productlistingapp.data

import com.example.productlistingapp.model.Product
import retrofit2.http.GET

// Retrofit turns this interface into real network code.
// `suspend` means it runs in a coroutine, so the UI doesn't freeze while waiting.
interface FakeStoreApi {

    // https://fakestoreapi.com/products
    @GET("products")
    suspend fun getProducts(): List<Product>
}
