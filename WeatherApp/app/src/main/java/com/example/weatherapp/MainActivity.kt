package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.weatherapp.ui.screens.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme

// ============================================================
// START: MainActivity
// The entry point of the app. Android opens this Activity first.
// setContent { } is where the Compose UI begins.
// ============================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lets the app draw behind the status bar and navigation bar
        enableEdgeToEdge()

        setContent {
            // The theme gives every screen the same colors and text styles
            WeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}
// ============================================================
// END: MainActivity
// ============================================================
