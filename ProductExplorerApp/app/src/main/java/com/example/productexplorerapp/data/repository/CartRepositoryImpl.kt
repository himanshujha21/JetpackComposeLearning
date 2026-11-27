package com.example.productexplorerapp.data.repository

import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

// LAYER: DATA (repository implementation)
// Why: the cart needs one place to live that every screen can share.
// Right now that place is memory. To keep the cart after the app closes,
// we would save it with Room or DataStore here, and nothing else would change.

// Keeps the cart in memory (it is empty again when the app is closed).
//
// @Singleton: Hilt creates only ONE cart for the whole app.
// That is why every screen sees the same items.
@Singleton
class CartRepositoryImpl @Inject constructor() : CartRepository {

    // _cartItems can be changed (only inside this class),
    // cartItems is the read-only version the screens use.
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    override val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    override fun addToCart(product: Product) {
        val itemInCart = _cartItems.value.find { it.product.id == product.id }

        if (itemInCart == null) {
            // Not in the cart yet: add it with quantity 1
            _cartItems.value = _cartItems.value + CartItem(product = product, quantity = 1)
        } else {
            // Already in the cart: just add one more
            increaseQuantity(product.id)
        }
    }

    override fun increaseQuantity(productId: Int) {
        _cartItems.value = _cartItems.value.map { item ->
            if (item.product.id == productId) item.copy(quantity = item.quantity + 1) else item
        }
    }

    override fun decreaseQuantity(productId: Int) {
        val itemInCart = _cartItems.value.find { it.product.id == productId }
        if (itemInCart == null) {
            return
        }

        if (itemInCart.quantity == 1) {
            // Going from 1 to 0 means the item leaves the cart
            removeFromCart(productId)
        } else {
            _cartItems.value = _cartItems.value.map { item ->
                if (item.product.id == productId) item.copy(quantity = item.quantity - 1) else item
            }
        }
    }

    override fun removeFromCart(productId: Int) {
        _cartItems.value = _cartItems.value.filter { it.product.id != productId }
    }

    override fun clearCart() {
        _cartItems.value = emptyList()
    }
}
