package com.example.productlistingapp.ui

import androidx.lifecycle.ViewModel
import com.example.productlistingapp.model.CartItem
import com.example.productlistingapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Holds the cart. All screens use the same CartViewModel (see AppNavigation),
// so when you add something on one screen, the other screens see it too.
class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // A one-time message for the cart screen to show in a Snackbar.
    // null means "nothing to show right now".
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun addToCart(product: Product) {
        val isInCart = _cartItems.value.any { it.product.id == product.id }
        if (isInCart) {
            increaseQuantity(product.id)
        } else {
            _cartItems.value = _cartItems.value + CartItem(product, quantity = 1)
        }
    }

    fun increaseQuantity(productId: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.product.id == productId) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decreaseQuantity(productId: Int) {
        val item = _cartItems.value.find { it.product.id == productId } ?: return

        // Going from 1 to 0 means the item should leave the cart
        if (item.quantity == 1) {
            removeFromCart(productId)
        } else {
            _cartItems.value = _cartItems.value.map {
                if (it.product.id == productId) it.copy(quantity = it.quantity - 1) else it
            }
        }
    }

    fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    // This is a demo app, so checkout just empties the cart and sets a "thank you" message
    fun checkout() {
        _cartItems.value = emptyList()
        _message.value = "Order placed! Thank you for shopping."
    }

    // Called by the screen after it has shown the message, so it isn't shown again
    fun messageShown() {
        _message.value = null
    }
}
