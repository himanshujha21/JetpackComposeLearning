package com.example.productexplorerapp.domain.repository

import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.model.Product
import kotlinx.coroutines.flow.StateFlow

// Keeps the items in the cart while the app is open.
//
// cartItems is a StateFlow: screens "collect" it and redraw on their own
// whenever an item is added or removed.
interface CartRepository {

    val cartItems: StateFlow<List<CartItem>>

    fun addToCart(product: Product)

    fun increaseQuantity(productId: Int)

    fun decreaseQuantity(productId: Int)

    fun removeFromCart(productId: Int)

    fun clearCart()
}
