package com.example.productlistingapp.ui

// The ways the user can sort the product list
enum class SortOption(val label: String) {
    Recommended("Recommended"),
    PriceLowToHigh("Price: Low to High"),
    PriceHighToLow("Price: High to Low"),
    TopRated("Top rated"),
}
