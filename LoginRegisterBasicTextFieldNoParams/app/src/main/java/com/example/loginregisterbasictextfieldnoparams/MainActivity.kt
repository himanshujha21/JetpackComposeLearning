package com.example.loginregisterbasictextfieldnoparams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.loginregisterbasictextfieldnoparams.ui.theme.AppTheme

// Screen that is currently shown: "login" or "register".
// It is state, so when a link on either screen changes it, setContent runs again
// and shows the other screen. Because of this the screens don't need parameters.
var currentScreen by mutableStateOf("login")

// ===== MainActivity Start =====
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Draw behind the status bar so the top wave reaches the top of the screen
        enableEdgeToEdge()

        // Everything inside setContent is Compose UI
        setContent {
            // ---- App Theme Start ----
            // AppTheme gives every screen the same colors and fonts
            AppTheme {

                // ---- Screen Switch Start ----
                if (currentScreen == "login") {
                    LoginScreen()
                } else {
                    RegisterScreen()
                }
                // ---- Screen Switch End ----

            }
            // ---- App Theme End ----
        }
    }
}
// ===== MainActivity End =====
