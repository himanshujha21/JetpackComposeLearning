package com.example.productexplorerapp.presentation.cart

import androidx.lifecycle.ViewModel
import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// Used by both the cart screen and the checkout screen.
//
// The cart items live in CartRepository (one cart for the whole app),
// so this ViewModel just passes the clicks on to it.
// There is no use case here: a use case that only calls one repository
// function would add a file without adding anything useful.
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems

    fun increaseQuantity(productId: Int) {
        cartRepository.increaseQuantity(productId)
    }

    fun decreaseQuantity(productId: Int) {
        cartRepository.decreaseQuantity(productId)
    }

    fun removeFromCart(productId: Int) {
        cartRepository.removeFromCart(productId)
    }

    // There is no real payment in this demo: placing the order just empties the cart
    fun placeOrder() {
        cartRepository.clearCart()
    }
}
