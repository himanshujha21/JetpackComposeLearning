package com.example.productexplorerapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// ============================================================
// START: CartIconButton
// The cart icon in the top bar, with a small red badge
// that shows how many items are in the cart.
// ============================================================
@Composable
fun CartIconButton(
    cartCount: Int,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        // BadgedBox puts the badge on the top-right corner of the icon
        BadgedBox(
            badge = {
                // ---------- Badge (only when the cart is not empty) ----------
                if (cartCount > 0) {
                    Badge { Text(cartCount.toString()) }
                }
            },
        ) {
            // ---------- Cart icon ----------
            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
        }
    }
}
// ============================================================
// END: CartIconButton
// ============================================================
