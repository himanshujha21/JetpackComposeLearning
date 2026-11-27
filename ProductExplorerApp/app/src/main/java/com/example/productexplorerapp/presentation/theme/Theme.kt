package com.example.productexplorerapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// LAYER: PRESENTATION (theme)
// Why: colors and styles live in one place, so every screen looks the same
// and a color change is made only once.

// Colors used when the phone is in light mode
private val LightColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = Mint,
    onPrimaryContainer = DeepGreen,
    secondary = DarkTeal,
    onSecondary = Color.White,
    surfaceVariant = LightGray,
)

// Colors used when the phone is in dark mode
private val DarkColorScheme = darkColorScheme(
    primary = TealLight,
    onPrimary = DeepGreen,
    primaryContainer = MintDark,
    onPrimaryContainer = Mint,
    secondary = TealLight,
    onSecondary = DeepGreen,
)

// ============================================================
// START: ProductExplorerAppTheme
// Wraps the whole app (see MainActivity) so every screen
// uses the same colors and text styles.
// ============================================================
@Composable
fun ProductExplorerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // ---------- Pick light or dark colors ----------
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // ---------- Apply the theme to everything inside ----------
    // Typography() = Material's default text styles (titleLarge, bodyMedium, ...)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content,
    )
}
// ============================================================
// END: ProductExplorerAppTheme
// ============================================================
