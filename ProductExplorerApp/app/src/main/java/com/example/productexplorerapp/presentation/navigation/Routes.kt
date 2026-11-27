package com.example.productexplorerapp.presentation.navigation

import kotlinx.serialization.Serializable

// LAYER: PRESENTATION (navigation)
// Why: routes are classes, so the compiler checks that we pass the right
// arguments (a wrong type for productId would not even build).

// Every screen has a "route": the address we use to open it.
// @Serializable lets Navigation Compose save the route (and its arguments).

// The first screen: the grid of products
@Serializable
object ProductListRoute

// The detail screen needs to know WHICH product to show,
// so its route carries the product id as an argument.
@Serializable
data class ProductDetailRoute(val productId: Int)

@Serializable
object CartRoute

@Serializable
object CheckoutRoute
