package com.example.productexplorerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.productexplorerapp.presentation.cart.CartScreen
import com.example.productexplorerapp.presentation.cart.CheckoutScreen
import com.example.productexplorerapp.presentation.productdetail.ProductDetailScreen
import com.example.productexplorerapp.presentation.productlist.ProductListScreen

// LAYER: PRESENTATION (navigation)
// Why: all the screens and the moves between them are in one file, so it is
// easy to see how the app flows. Screens only get lambdas like onBackClick,
// so they don't need to know about the NavController.

// ============================================================
// START: AppNavigation
// Sets up all the screens and how we move between them.
//
// NAVIGATION in short:
// - NavController: keeps the list of open screens (the "back stack").
//   navigate(route) opens a screen, popBackStack() goes back.
// - NavHost: shows the screen for the current route.
// - composable<Route> { }: says which screen belongs to which route.
//
// Each screen gets its own ViewModel with hiltViewModel().
// The cart is still shared, because every ViewModel gets the
// same CartRepository (@Singleton) from Hilt.
// ============================================================
@Composable
fun AppNavigation() {
    // remember... keeps the same NavController across recompositions
    val navController = rememberNavController()

    // startDestination = the first screen shown when the app opens
    NavHost(navController = navController, startDestination = ProductListRoute) {

        // ---------- Product list ----------
        composable<ProductListRoute> {
            ProductListScreen(
                onProductClick = { productId ->
                    // Open the detail screen and send the product id with it
                    navController.navigate(ProductDetailRoute(productId))
                },
                onCartClick = { navController.navigate(CartRoute) },
            )
        }

        // ---------- Product detail ----------
        // The ViewModel reads the productId itself (see ProductDetailViewModel)
        composable<ProductDetailRoute> {
            ProductDetailScreen(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(CartRoute) },
            )
        }

        // ---------- Cart ----------
        composable<CartRoute> {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                onCheckoutClick = { navController.navigate(CheckoutRoute) },
            )
        }

        // ---------- Checkout ----------
        composable<CheckoutRoute> {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onOrderPlaced = {
                    // Go back to the product list and close the screens on top of it
                    // (detail, cart, checkout), so Back doesn't return to an empty checkout.
                    navController.popBackStack(ProductListRoute, inclusive = false)
                },
            )
        }
    }
}
// ============================================================
// END: AppNavigation
// ============================================================
