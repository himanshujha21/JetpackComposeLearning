package com.example.loginregisterwithbasictextfield.utils

import android.util.Patterns

/*
 * Simple validation functions for the forms.
 *
 * Each function returns:
 *  - an error message if the input is wrong
 *  - null if the input is correct
 *
 * These are normal Kotlin functions, not Composables, so they contain no UI code.
 */

fun validateName(name: String): String? {
    if (name.isBlank()) return "Name is required"
    if (name.trim().length < 3) return "Name must be at least 3 characters"
    return null
}

fun validateEmail(email: String): String? {
    if (email.isBlank()) return "Email is required"
    // Patterns.EMAIL_ADDRESS is Android's built-in email format check
    if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) return "Enter a valid email"
    return null
}

fun validatePassword(password: String): String? {
    if (password.isEmpty()) return "Password is required"
    if (password.length < 6) return "Password must be at least 6 characters"
    return null
}

fun validateConfirmPassword(password: String, confirmPassword: String): String? {
    if (confirmPassword.isEmpty()) return "Please confirm your password"
    if (password != confirmPassword) return "Passwords do not match"
    return null
}
