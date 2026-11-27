package com.example.productexplorerapp.presentation.productdetail

import com.example.productexplorerapp.domain.model.Product

// What the detail screen needs to draw itself.
// product is null until it has been loaded.
data class ProductDetailUiState(
    val isLoading: Boolean = true,
    val product: Product? = null,
    val errorMessage: String? = null,
)
