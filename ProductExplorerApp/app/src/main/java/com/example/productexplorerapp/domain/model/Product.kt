package com.example.productexplorerapp.domain.model

// LAYER: DOMAIN (model)
// Why: screens and use cases use this class, so they don't depend on the API format.

// The product the rest of the app works with.
//
// This is a "domain model": plain Kotlin, no Retrofit, no JSON annotations.
// The JSON from the API looks a bit different (see ProductDto) and the mapper
// turns it into this class. So if the API changes, only the data layer changes.
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val rating: Double,
    val ratingCount: Int,
)
