package com.example.productlistingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productlistingapp.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Loads the products and remembers the search text, category and sort option.
// A ViewModel survives screen rotation, so we don't download the products again.
class ProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    // Calling the API is a side effect (it talks to the outside world).
    // We start it here in init, NOT inside a Composable:
    // - a Composable can run (recompose) many times, so the call could repeat
    // - init runs only once, when the ViewModel is created,
    //   and the ViewModel survives screen rotation and going to other screens
    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val products = RetrofitClient.api.getProducts()
                _uiState.value = _uiState.value.copy(isLoading = false, products = products)
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
    }

    fun selectCategory(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun selectSortOption(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(sortOption = sortOption)
    }
}
