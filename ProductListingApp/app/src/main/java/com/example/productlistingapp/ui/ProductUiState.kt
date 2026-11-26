package com.example.productlistingapp.ui

import com.example.productlistingapp.model.Product

// Everything the product list screen needs to show
data class ProductUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val products: List<Product> = emptyList(),
    val searchText: String = "",
    val selectedCategory: String? = null, // null means "All"
    val sortOption: SortOption = SortOption.Recommended,
) {

    // e.g. "electronics", "jewelery" - taken from the products we already have
    val categories: List<String>
        get() = products.map { it.category }.distinct()

    // The products after search, category filter and sorting
    val visibleProducts: List<Product>
        get() {
            var result = products

            if (selectedCategory != null) {
                result = result.filter { it.category == selectedCategory }
            }

            if (searchText.isNotBlank()) {
                result = result.filter { it.title.contains(searchText, ignoreCase = true) }
            }

            return when (sortOption) {
                SortOption.Recommended -> result
                SortOption.PriceLowToHigh -> result.sortedBy { it.price }
                SortOption.PriceHighToLow -> result.sortedByDescending { it.price }
                SortOption.TopRated -> result.sortedByDescending { it.rating.rate }
            }
        }
}
