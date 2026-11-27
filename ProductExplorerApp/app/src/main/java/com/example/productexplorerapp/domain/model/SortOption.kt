package com.example.productexplorerapp.domain.model

// LAYER: DOMAIN (model)
// Why here and not in the UI? Sorting is an app rule. FilterProductsUseCase
// uses it to sort, and it doesn't need to know anything about Compose.

// The ways the user can sort the product list.
// label is the text shown in the sort menu.
enum class SortOption(val label: String) {
    DEFAULT("Default"),
    PRICE_LOW_TO_HIGH("Price: low to high"),
    PRICE_HIGH_TO_LOW("Price: high to low"),
    RATING("Top rated"),
    NAME("Name: A to Z"),
}
