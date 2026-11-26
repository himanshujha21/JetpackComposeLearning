package com.example.productlistingapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.productlistingapp.ui.CartViewModel
import com.example.productlistingapp.ui.ProductViewModel
import com.example.productlistingapp.ui.screens.CartScreen
import com.example.productlistingapp.ui.screens.ProductDetailScreen
import com.example.productlistingapp.ui.screens.ProductListScreen

// ============================================================
// START: AppNavigation
// Sets up all the screens and how we move between them.
//
// NAVIGATION in short:
// - NavController: keeps the list of open screens (the "back stack")
//   navigate(route) opens a screen, popBackStack() goes back.
// - NavHost: shows the screen for the current route.
// - composable<Route> { }: says which screen belongs to which route.
//
// Back stack example:
//   App starts      -> [ProductList]
//   Tap a product   -> [ProductList, ProductDetail]
//   Tap cart icon   -> [ProductList, ProductDetail, Cart]
//   Press back      -> [ProductList, ProductDetail]
// ============================================================
@Composable
fun AppNavigation() {
    // remember... so the same NavController is kept across recompositions
    val navController = rememberNavController()

    // ViewModels are created here, outside NavHost, so all screens share the same ones.
    // This way the cart stays the same on every screen.
    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    // startDestination = the first screen shown when the app opens
    NavHost(navController = navController, startDestination = ProductListRoute) {

        // ---------- Product list screen ----------
        composable<ProductListRoute> {
            ProductListScreen(
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                onProductClick = { productId ->
                    // Open the detail screen and send the product id with it
                    navController.navigate(ProductDetailRoute(productId))
                },
                onCartClick = {
                    navController.navigate(CartRoute)
                }
            )
        }

        // ---------- Product detail screen ----------
        composable<ProductDetailRoute> { backStackEntry ->
            // toRoute() gives back the route we navigated with,
            // so we can read the productId that was sent
            val productId = backStackEntry.toRoute<ProductDetailRoute>().productId

            ProductDetailScreen(
                productId = productId,
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                onBackClick = {
                    // Close this screen and go back to the previous one
                    navController.popBackStack()
                },
                onCartClick = {
                    navController.navigate(CartRoute)
                }
            )
        }

        // ---------- Cart screen ----------
        composable<CartRoute> {
            CartScreen(
                cartViewModel = cartViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
// ============================================================
// END: AppNavigation
// ============================================================
