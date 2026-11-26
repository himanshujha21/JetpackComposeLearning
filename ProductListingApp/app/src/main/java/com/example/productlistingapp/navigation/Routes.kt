package com.example.productlistingapp.navigation

import kotlinx.serialization.Serializable

// NAVIGATION: Routes
// A route is the "address" of a screen. To open a screen we pass its route
// to navController.navigate(), and the NavHost shows the matching screen.
//
// Routes are marked @Serializable so Navigation can save them
// (for example when the phone rotates) and pass their values for us.

// Product list screen. Needs no extra data, so it's an object.
@Serializable
object ProductListRoute

// Product detail screen. It needs to know which product to show,
// so it's a data class with the productId as an argument.
// Example: navController.navigate(ProductDetailRoute(productId = 5))
@Serializable
data class ProductDetailRoute(val productId: Int)

// Cart screen. Needs no extra data.
@Serializable
object CartRoute
