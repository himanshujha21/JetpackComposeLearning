package com.example.productexplorerapp.presentation.productlist

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.model.SortOption

// Everything the product list screen needs to draw itself, in one object.
// The screen just reads it; only the ViewModel changes it.
data class ProductListUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,

    // The products to show, after search, category filter and sorting
    val products: List<Product> = emptyList(),

    // For the chips row, e.g. ["electronics", "jewelery", ...]
    val categories: List<String> = emptyList(),

    val searchText: String = "",
    val selectedCategory: String? = null, // null = "All"
    val sortOption: SortOption = SortOption.DEFAULT,
)
