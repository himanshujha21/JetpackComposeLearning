package com.example.productexplorerapp.domain.usecase

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.model.SortOption
import javax.inject.Inject

// Search, category filter and sorting, all in one place.
//
// This is plain Kotlin (no Android, no network), so it is easy to read
// and easy to test (see FilterProductsUseCaseTest).
class FilterProductsUseCase @Inject constructor() {

    fun filterProducts(
        products: List<Product>,
        searchText: String,
        category: String?,
        sortOption: SortOption,
    ): List<Product> {
        // 1. Keep only products whose name contains the search text
        //    (ignoreCase = true, so "shirt" also finds "Shirt")
        var result = products.filter { it.title.contains(searchText.trim(), ignoreCase = true) }

        // 2. Keep only the selected category (null means "All")
        if (category != null) {
            result = result.filter { it.category == category }
        }

        // 3. Sort the list
        result = when (sortOption) {
            SortOption.DEFAULT -> result
            SortOption.PRICE_LOW_TO_HIGH -> result.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> result.sortedByDescending { it.price }
            SortOption.RATING -> result.sortedByDescending { it.rating }
            SortOption.NAME -> result.sortedBy { it.title }
        }

        return result
    }
}
