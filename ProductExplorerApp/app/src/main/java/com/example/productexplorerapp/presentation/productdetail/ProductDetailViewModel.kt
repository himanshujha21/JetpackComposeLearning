package com.example.productexplorerapp.presentation.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.repository.CartRepository
import com.example.productexplorerapp.domain.usecase.GetProductDetailsUseCase
import com.example.productexplorerapp.presentation.navigation.ProductDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// LAYER: PRESENTATION (ViewModel)
// Why: the ViewModel holds the screen data and talks to the use cases and
// repositories. The Composable only draws what it gets, so it stays simple.

// The ViewModel for the product detail screen.
//
// SavedStateHandle holds the navigation arguments of this screen.
// Hilt gives it to us, and toRoute() turns it back into the
// ProductDetailRoute we navigated with, so we can read the productId.
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val productId: Int = savedStateHandle.toRoute<ProductDetailRoute>().productId

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    // The cart, for the badge on the cart icon
    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState(isLoading = true)

            try {
                val product = getProductDetailsUseCase.getProductDetails(productId)
                _uiState.value = ProductDetailUiState(isLoading = false, product = product)
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState(
                    isLoading = false,
                    errorMessage = "Couldn't load this product. Check your internet and try again.",
                )
            }
        }
    }

    fun addToCart() {
        // Only add it if the product has been loaded
        val product = _uiState.value.product
        if (product != null) {
            cartRepository.addToCart(product)
        }
    }
}
