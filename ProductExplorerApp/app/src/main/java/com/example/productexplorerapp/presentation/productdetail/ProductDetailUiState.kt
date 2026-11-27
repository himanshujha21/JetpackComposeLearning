package com.example.productexplorerapp.presentation.productdetail

import com.example.productexplorerapp.domain.model.Product

// LAYER: PRESENTATION (UI state)
// Why one class? The screen has a single thing to read, so it can never
// show "loading" and "error" at the same time by mistake.

// What the detail screen needs to draw itself.
// product is null until it has been loaded.
data class ProductDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val errorMessage: String? = null,
)
