package com.example.loginregisterwithbasictextfield

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.loginregisterwithbasictextfield.ui.screens.HomeScreen
import com.example.loginregisterwithbasictextfield.ui.screens.LoginScreen
import com.example.loginregisterwithbasictextfield.ui.screens.RegisterScreen
import com.example.loginregisterwithbasictextfield.ui.theme.LoginRegisterWithBasicTextFieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Draw behind the status bar, with dark icons because our background is light
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
        )

        // ---------- setContent START (Compose UI begins here) ----------
        setContent {
            LoginRegisterWithBasicTextFieldTheme {
                App()
            }
        }
        // ---------- setContent END ----------
    }
}

enum class Screen { Login, Register, Home }

// ===================== App START =====================
// No navigation library here. We just keep the current screen in a variable
// and show the matching screen.
@Composable
fun App() {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.Login) }
    var userName by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    when (currentScreen) {

        // ---------- Login screen ----------
        Screen.Login -> LoginScreen(
            onLoginSuccess = { email ->
                userName = email
                currentScreen = Screen.Home
            },
            onForgotPasswordClick = {
                Toast.makeText(context, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
            },
            onRegisterClick = { currentScreen = Screen.Register },
        )

        // ---------- Register screen ----------
        Screen.Register -> RegisterScreen(
            onRegisterSuccess = { name ->
                userName = name
                currentScreen = Screen.Home
            },
            onLoginClick = { currentScreen = Screen.Login },
        )

        // ---------- Home screen ----------
        Screen.Home -> HomeScreen(
            userName = userName,
            onLogoutClick = {
                userName = ""
                currentScreen = Screen.Login
            },
        )
    }
}
// ===================== App END =====================
