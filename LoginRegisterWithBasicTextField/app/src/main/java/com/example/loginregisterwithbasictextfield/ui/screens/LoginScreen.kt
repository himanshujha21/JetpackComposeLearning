package com.example.loginregisterwithbasictextfield.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginregisterwithbasictextfield.R
import com.example.loginregisterwithbasictextfield.ui.components.BottomTextLink
import com.example.loginregisterwithbasictextfield.ui.components.CustomTextField
import com.example.loginregisterwithbasictextfield.ui.components.IconHeader
import com.example.loginregisterwithbasictextfield.ui.components.LinkText
import com.example.loginregisterwithbasictextfield.ui.components.PrimaryButton
import com.example.loginregisterwithbasictextfield.ui.components.WaveBackground
import com.example.loginregisterwithbasictextfield.utils.validateEmail
import com.example.loginregisterwithbasictextfield.utils.validatePassword

// ===================== LoginScreen START =====================
@Composable
fun LoginScreen(
    onLoginSuccess: (email: String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    // rememberSaveable keeps the values when the screen rotates
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    fun login() {
        emailError = validateEmail(email)
        passwordError = validatePassword(password)

        if (emailError == null && passwordError == null) {
            onLoginSuccess(email.trim())
        }
    }

    // ---------- WaveBackground START ----------
    WaveBackground {

        // ---------- Column START (all screen content) ----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding() // push content up when the keyboard opens
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Header: lock icon + title + subtitle
            IconHeader(
                icon = R.drawable.ic_lock,
                title = stringResource(R.string.login_title),
                subtitle = stringResource(R.string.login_subtitle),
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email field
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                hint = stringResource(R.string.hint_email),
                icon = R.drawable.ic_email,
                errorMessage = emailError,
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field
            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                hint = stringResource(R.string.hint_password),
                icon = R.drawable.ic_lock_outline,
                isPassword = true,
                errorMessage = passwordError,
                imeAction = ImeAction.Done,
                onDone = { login() },
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Login button
            PrimaryButton(text = stringResource(R.string.login_button), onClick = { login() })

            Spacer(modifier = Modifier.height(12.dp))

            // Forgot Password link
            LinkText(text = stringResource(R.string.forgot_password), onClick = onForgotPasswordClick)

            Spacer(modifier = Modifier.height(32.dp))

            // "Don't have an account? Register"
            BottomTextLink(
                message = stringResource(R.string.no_account),
                linkText = stringResource(R.string.go_to_register),
                onLinkClick = onRegisterClick,
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
        // ---------- Column END ----------
    }
    // ---------- WaveBackground END ----------
}
// ===================== LoginScreen END =====================


// ===================== Preview START =====================
@Preview(showBackground = true, heightDp = 800)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(onLoginSuccess = {}, onForgotPasswordClick = {}, onRegisterClick = {})
}
// ===================== Preview END =====================
