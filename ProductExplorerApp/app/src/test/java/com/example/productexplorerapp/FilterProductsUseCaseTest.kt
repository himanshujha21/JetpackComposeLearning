package com.example.productexplorerapp

import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.model.SortOption
import com.example.productexplorerapp.domain.usecase.FilterProductsUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// FilterProductsUseCase is plain Kotlin, so we can test it without
// a phone, an emulator or the internet. Run it with the green arrow in Android Studio.
class FilterProductsUseCaseTest {

    private val useCase = FilterProductsUseCase()

    private val backpack = Product(1, "Backpack", 109.95, "", "men's clothing", "", 3.9, 120)
    private val ring = Product(2, "Silver Ring", 10.99, "", "jewelery", "", 4.6, 400)
    private val shirt = Product(3, "Slim Fit Shirt", 22.30, "", "men's clothing", "", 4.1, 259)

    private val allProducts = listOf(backpack, ring, shirt)

    @Test
    fun search_ignoresUpperAndLowerCase() {
        val result = useCase.filterProducts(allProducts, "SHIRT", null, SortOption.DEFAULT)

        assertEquals(listOf(shirt), result)
    }

    @Test
    fun category_keepsOnlyThatCategory() {
        val result = useCase.filterProducts(allProducts, "", "men's clothing", SortOption.DEFAULT)

        assertEquals(listOf(backpack, shirt), result)
    }

    @Test
    fun sortByPriceLowToHigh() {
        val result = useCase.filterProducts(allProducts, "", null, SortOption.PRICE_LOW_TO_HIGH)

        assertEquals(listOf(ring, shirt, backpack), result)
    }

    @Test
    fun sortByRating_bestFirst() {
        val result = useCase.filterProducts(allProducts, "", null, SortOption.RATING)

        assertEquals(listOf(ring, shirt, backpack), result)
    }

    @Test
    fun searchWithNoMatch_returnsEmptyList() {
        val result = useCase.filterProducts(allProducts, "laptop", null, SortOption.DEFAULT)

        assertTrue(result.isEmpty())
    }
}
