package com.example.productexplorerapp.domain.model

// The ways the user can sort the product list.
// label is the text shown in the sort menu.
enum class SortOption(val label: String) {
    DEFAULT("Default"),
    PRICE_LOW_TO_HIGH("Price: low to high"),
    PRICE_HIGH_TO_LOW("Price: high to low"),
    RATING("Top rated"),
    NAME("Name: A to Z"),
}
