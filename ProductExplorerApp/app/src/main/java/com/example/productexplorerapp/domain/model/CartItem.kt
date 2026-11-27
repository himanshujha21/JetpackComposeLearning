package com.example.productexplorerapp.domain.model

// LAYER: DOMAIN (model)
// Why: "what is in the cart" is part of the app's own logic, not the UI or the API.

// One line in the cart: which product and how many of it.
data class CartItem(
    val product: Product,
    val quantity: Int,
) {
    // Price for this line, e.g. 2 x $10.00 = $20.00
    val totalPrice: Double
        get() = product.price * quantity
}
