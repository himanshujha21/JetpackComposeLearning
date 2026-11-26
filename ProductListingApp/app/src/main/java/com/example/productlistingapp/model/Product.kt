package com.example.productlistingapp.model

import kotlinx.serialization.Serializable

// One product from the Fake Store API.
// The property names match the JSON keys, so kotlinx.serialization can fill them in.
@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
)

// "rating": { "rate": 3.9, "count": 120 }
@Serializable
data class Rating(
    val rate: Double,
    val count: Int,
)
