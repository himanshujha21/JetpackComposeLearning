package com.example.productexplorerapp.presentation.util

import java.util.Locale

// LAYER: PRESENTATION (helper)
// Why here and not in domain? Showing "$109.95" is only about how things look on screen.

// 109.95 -> "$109.95", 7.5 -> "$7.50"
// Locale.US makes sure we always get a dot, never a comma.
fun formatPrice(price: Double): String {
    return String.format(Locale.US, "$%.2f", price)
}
