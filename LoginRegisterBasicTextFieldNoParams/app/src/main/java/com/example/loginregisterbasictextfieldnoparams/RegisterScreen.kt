package com.example.loginregisterbasictextfieldnoparams

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginregisterbasictextfieldnoparams.ui.theme.AppTheme
import com.example.loginregisterbasictextfieldnoparams.ui.theme.DarkText
import com.example.loginregisterbasictextfieldnoparams.ui.theme.FieldBorder
import com.example.loginregisterbasictextfieldnoparams.ui.theme.GrayText
import com.example.loginregisterbasictextfieldnoparams.ui.theme.LavenderBorder
import com.example.loginregisterbasictextfieldnoparams.ui.theme.LightLavender
import com.example.loginregisterbasictextfieldnoparams.ui.theme.PrimaryPurple

// ===== RegisterScreen Start =====
// Built the same way as LoginScreen. See the comments there for state,
// Box, Column, Row, Modifier and OutlinedTextField.
@Composable
fun RegisterScreen() {
    val context = LocalContext.current

    // One state value per field, plus one for each password eye button
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PrimaryPurple,
        unfocusedBorderColor = FieldBorder,
        cursorColor = PrimaryPurple
    )

    // ---- Background Box Start ----
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ---- Top Wave Start ----
        Image(
            painter = painterResource(R.drawable.wave_top),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .align(Alignment.TopCenter)
        )
        // ---- Top Wave End ----

        // ---- Bottom Wave Start ----
        Image(
            painter = painterResource(R.drawable.wave_bottom),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
        )
        // ---- Bottom Wave End ----

        // ---- Form Column Start ----
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            // ---- Person Icon Start ----
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(LightLavender, CircleShape)
                    .border(2.dp, LavenderBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person_filled),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(64.dp)
                )
                // Small "+" moved to the bottom-right of the person icon
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier
                        .size(28.dp)
                        .offset(x = 26.dp, y = 20.dp)
                )
            }
            // ---- Person Icon End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Title Start ----
            Text(
                text = "Register",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            // ---- Title End ----

            Spacer(modifier = Modifier.height(8.dp))

            // ---- Subtitle Start ----
            Text(
                text = "Create a new account to get started.",
                fontSize = 16.sp,
                color = GrayText
            )
            // ---- Subtitle End ----

            Spacer(modifier = Modifier.height(32.dp))

            // ---- Full Name Field Start ----
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Full Name") },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_person_outline), contentDescription = null)
                },
                singleLine = true,
                shape = RoundedCornerShape(18.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Full Name Field End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Email Field Start ----
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_mail_outline), contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(18.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Email Field End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Password Field Start ----
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_lock_outline), contentDescription = null)
                },
                trailingIcon = {
                    // ---- Eye Button Start ----
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                            ),
                            contentDescription = "Toggle password visibility"
                        )
                    }
                    // ---- Eye Button End ----
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(18.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Password Field End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Confirm Password Field Start ----
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_lock_outline), contentDescription = null)
                },
                trailingIcon = {
                    // ---- Eye Button Start ----
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (confirmPasswordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                            ),
                            contentDescription = "Toggle password visibility"
                        )
                    }
                    // ---- Eye Button End ----
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(18.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Confirm Password Field End ----

            Spacer(modifier = Modifier.height(32.dp))

            // ---- Register Button Start ----
            // Checks that every field is filled and both passwords are the same
            Button(
                onClick = {
                    if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
            ) {
                Text(text = "Register", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            // ---- Register Button End ----

            Spacer(modifier = Modifier.height(20.dp))

            // ---- Login Link Start ----
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?", fontSize = 16.sp, color = DarkText)
                // Changing currentScreen (in MainActivity.kt) goes back to the Login screen
                TextButton(onClick = { currentScreen = "login" }) {
                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple
                    )
                }
            }
            // ---- Login Link End ----

            Spacer(modifier = Modifier.height(100.dp))
        }
        // ---- Form Column End ----
    }
    // ---- Background Box End ----
}
// ===== RegisterScreen End =====


// ===== RegisterScreen Preview Start =====
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen()
    }
}
// ===== RegisterScreen Preview End =====
