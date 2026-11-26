package com.example.productlistingapp.util

import java.util.Locale

// 109.95 -> "$109.95"
fun formatPrice(price: Double): String {
    return "$" + String.format(Locale.US, "%.2f", price)
}
