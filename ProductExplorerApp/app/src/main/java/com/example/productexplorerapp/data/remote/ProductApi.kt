package com.example.productexplorerapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

// LAYER: DATA (remote data source)
// Why: this is the only file that knows the API addresses. Retrofit is our
// remote data source: it goes to the internet and brings back JSON.
// If the server or the endpoints change, this is the file we change.

// The Fake Store API endpoints we use. Retrofit writes the code behind these for us.
// Docs: https://fakestoreapi.com/docs
interface ProductApi {

    // GET https://fakestoreapi.com/products
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    // GET https://fakestoreapi.com/products/5
    // {id} in the path is replaced with the value of the id parameter
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
}
