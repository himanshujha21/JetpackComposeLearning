package com.example.productexplorerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.productexplorerapp.presentation.navigation.AppNavigation
import com.example.productexplorerapp.presentation.theme.ProductExplorerAppTheme
import dagger.hilt.android.AndroidEntryPoint

// ============================================================
// START: MainActivity
// The only Activity. It just sets the theme and the navigation;
// all screens are Composables inside AppNavigation.
//
// @AndroidEntryPoint lets Hilt give ViewModels to the screens
// that live inside this Activity.
// ============================================================
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Draw behind the status bar; Scaffold adds the right padding for us
        enableEdgeToEdge()
        setContent {
            ProductExplorerAppTheme {
                AppNavigation()
            }
        }
    }
}
// ============================================================
// END: MainActivity
// ============================================================
