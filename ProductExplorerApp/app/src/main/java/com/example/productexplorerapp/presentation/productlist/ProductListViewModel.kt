package com.example.productexplorerapp.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.model.SortOption
import com.example.productexplorerapp.domain.repository.CartRepository
import com.example.productexplorerapp.domain.usecase.FilterProductsUseCase
import com.example.productexplorerapp.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// The ViewModel for the product list screen.
//
// @HiltViewModel + @Inject constructor: Hilt creates this ViewModel and
// passes in the use cases and the cart. The screen gets it with hiltViewModel().
//
// A ViewModel survives screen rotation, so we don't download the products again.
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val filterProductsUseCase: FilterProductsUseCase,
    private val cartRepository: CartRepository,
) : ViewModel() {

    // _uiState can be changed here, uiState is the read-only version for the screen
    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    // The cart, for the badge on the cart icon
    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems

    // All products from the API. We keep them so we can search and sort
    // again and again without downloading them every time.
    private var allProducts: List<Product> = emptyList()

    // Calling the API is a side effect (it talks to the outside world).
    // We start it here in init, NOT inside a Composable:
    // a Composable can run (recompose) many times, but init runs only once.
    init {
        loadProducts()
    }

    fun loadProducts() {
        // viewModelScope.launch starts a coroutine, so the network call
        // runs in the background. It is cancelled when the ViewModel is cleared.
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                allProducts = getProductsUseCase.getProducts()

                // Build the category list from the products, e.g. ["electronics", "jewelery"]
                val categories = allProducts.map { it.category }.distinct()

                _uiState.value = _uiState.value.copy(isLoading = false, categories = categories)
                updateVisibleProducts()
            } catch (e: Exception) {
                // No internet, server down, etc.
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Couldn't load products. Check your internet and try again.",
                )
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _uiState.value = _uiState.value.copy(searchText = text)
        updateVisibleProducts()
    }

    fun onCategorySelected(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        updateVisibleProducts()
    }

    fun onSortOptionSelected(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(sortOption = sortOption)
        updateVisibleProducts()
    }

    fun addToCart(product: Product) {
        cartRepository.addToCart(product)
    }

    // Runs the search + filter + sort on all products,
    // and puts the result in the UI state.
    private fun updateVisibleProducts() {
        val state = _uiState.value
        val visibleProducts = filterProductsUseCase.filterProducts(
            products = allProducts,
            searchText = state.searchText,
            category = state.selectedCategory,
            sortOption = state.sortOption,
        )
        _uiState.value = state.copy(products = visibleProducts)
    }
}
