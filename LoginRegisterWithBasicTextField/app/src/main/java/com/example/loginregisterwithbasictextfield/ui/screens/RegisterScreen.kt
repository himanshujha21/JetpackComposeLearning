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
import com.example.loginregisterwithbasictextfield.ui.components.PrimaryButton
import com.example.loginregisterwithbasictextfield.ui.components.WaveBackground
import com.example.loginregisterwithbasictextfield.utils.validateConfirmPassword
import com.example.loginregisterwithbasictextfield.utils.validateEmail
import com.example.loginregisterwithbasictextfield.utils.validateName
import com.example.loginregisterwithbasictextfield.utils.validatePassword

// ===================== RegisterScreen START =====================
@Composable
fun RegisterScreen(
    onRegisterSuccess: (name: String) -> Unit,
    onLoginClick: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }

    fun register() {
        nameError = validateName(name)
        emailError = validateEmail(email)
        passwordError = validatePassword(password)
        confirmPasswordError = validateConfirmPassword(password, confirmPassword)

        if (nameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
            onRegisterSuccess(name.trim())
        }
    }

    // ---------- WaveBackground START ----------
    WaveBackground {

        // ---------- Column START (all screen content) ----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // Header: person-add icon + title + subtitle
            IconHeader(
                icon = R.drawable.ic_person_add,
                title = stringResource(R.string.register_title),
                subtitle = stringResource(R.string.register_subtitle),
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Full Name field
            CustomTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = null
                },
                hint = stringResource(R.string.hint_full_name),
                icon = R.drawable.ic_person,
                errorMessage = nameError,
            )

            Spacer(modifier = Modifier.height(14.dp))

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

            Spacer(modifier = Modifier.height(14.dp))

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
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Confirm Password field
            CustomTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                hint = stringResource(R.string.hint_confirm_password),
                icon = R.drawable.ic_lock_outline,
                isPassword = true,
                errorMessage = confirmPasswordError,
                imeAction = ImeAction.Done,
                onDone = { register() },
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Register button
            PrimaryButton(text = stringResource(R.string.register_button), onClick = { register() })

            Spacer(modifier = Modifier.height(32.dp))

            // "Already have an account? Login"
            BottomTextLink(
                message = stringResource(R.string.have_account),
                linkText = stringResource(R.string.go_to_login),
                onLinkClick = onLoginClick,
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
        // ---------- Column END ----------
    }
    // ---------- WaveBackground END ----------
}
// ===================== RegisterScreen END =====================


// ===================== Preview START =====================
@Preview(showBackground = true, heightDp = 800)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onRegisterSuccess = {}, onLoginClick = {})
}
// ===================== Preview END =====================
