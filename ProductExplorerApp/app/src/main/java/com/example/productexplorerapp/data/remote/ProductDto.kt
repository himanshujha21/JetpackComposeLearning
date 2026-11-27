package com.example.productexplorerapp.data.remote

import kotlinx.serialization.Serializable

// LAYER: DATA (API model)
// Why a separate class from Product? The JSON shape belongs to the server, not to us.
// Keeping it in the data layer means a change in the JSON never reaches the screens.

// DTO = Data Transfer Object: a class that looks EXACTLY like the JSON from the API.
//
// One product from https://fakestoreapi.com/products looks like this:
// {
//   "id": 1,
//   "title": "Fjallraven - Foldsack No. 1 Backpack",
//   "price": 109.95,
//   "description": "Your perfect pack for everyday use...",
//   "category": "men's clothing",
//   "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
//   "rating": { "rate": 3.9, "count": 120 }
// }
//
// @Serializable lets kotlinx.serialization turn that JSON into this class.
// The property names must match the JSON keys.
@Serializable
data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDto,
)

// The nested "rating": { "rate": 3.9, "count": 120 } object
@Serializable
data class RatingDto(
    val rate: Double,
    val count: Int,
)
