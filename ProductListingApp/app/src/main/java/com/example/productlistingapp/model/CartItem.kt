package com.example.productlistingapp.model

// A product in the cart and how many of it we want
data class CartItem(
    val product: Product,
    val quantity: Int,
)
