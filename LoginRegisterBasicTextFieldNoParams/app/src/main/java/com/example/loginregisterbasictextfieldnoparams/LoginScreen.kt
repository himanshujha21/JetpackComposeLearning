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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import com.example.loginregisterbasictextfieldnoparams.ui.theme.DividerGray
import com.example.loginregisterbasictextfieldnoparams.ui.theme.FieldBorder
import com.example.loginregisterbasictextfieldnoparams.ui.theme.GrayText
import com.example.loginregisterbasictextfieldnoparams.ui.theme.LavenderBorder
import com.example.loginregisterbasictextfieldnoparams.ui.theme.LightLavender
import com.example.loginregisterbasictextfieldnoparams.ui.theme.PrimaryPurple

// ===== LoginScreen Start =====
// A @Composable function describes what the screen looks like.
// Compose runs it again whenever a state value it uses changes (called recomposition).
@Composable
fun LoginScreen() {
    // Needed for showing Toast messages
    val context = LocalContext.current

    // State: mutableStateOf holds a value, remember keeps it when the screen redraws.
    // Thanks to "by" we can write email = "abc" instead of email.value = "abc".
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // colors() keeps the default Material colors and only changes the ones we pass
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PrimaryPurple,
        unfocusedBorderColor = FieldBorder,
        cursorColor = PrimaryPurple
    )

    // ---- Background Box Start ----
    // Box puts its children on top of each other, so the waves sit behind the form.
    // Modifier sets size, background, padding and so on. Each call is applied in order.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ---- Top Wave Start ----
        // Image shows a picture from res/drawable
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
        // Column puts its children one below another.
        // verticalScroll makes it scrollable when the keyboard is open or the screen is small.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            // ---- Lock Icon Start ----
            // background() fills the circle and border() draws a ring around it
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(LightLavender, CircleShape)
                    .border(2.dp, LavenderBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_lock_filled),
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(64.dp)
                )
            }
            // ---- Lock Icon End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Title Start ----
            // dp is for sizes and spacing, sp is for text size
            Text(
                text = "Login",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            // ---- Title End ----

            // Spacer adds empty space between two components
            Spacer(modifier = Modifier.height(8.dp))

            // ---- Subtitle Start ----
            Text(
                text = "Welcome back! Please login to your account.",
                fontSize = 16.sp,
                color = GrayText
            )
            // ---- Subtitle End ----

            Spacer(modifier = Modifier.height(40.dp))

            // ---- Email Field Start ----
            // value shows the current text, onValueChange saves each new key press into state.
            // leadingIcon is the icon on the left, shape rounds the border corners.
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

            Spacer(modifier = Modifier.height(20.dp))

            // ---- Password Field Start ----
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(painterResource(R.drawable.ic_lock_outline), contentDescription = null)
                },
                // trailingIcon is the icon on the right. Tapping the eye flips passwordVisible,
                // Compose redraws the field, and the password is shown or hidden.
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
                // Show dots when the password is hidden
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(18.dp),
                colors = fieldColors,
                modifier = Modifier.fillMaxWidth()
            )
            // ---- Password Field End ----

            Spacer(modifier = Modifier.height(32.dp))

            // ---- Login Button Start ----
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
            ) {
                Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            // ---- Login Button End ----

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Forgot Password Link Start ----
            // TextButton is a button without a background, used here like a link
            TextButton(onClick = {
                Toast.makeText(context, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
            }) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryPurple
                )
            }
            // ---- Forgot Password Link End ----

            Spacer(modifier = Modifier.height(20.dp))

            // ---- OR Divider Start ----
            // Row puts its children side by side. weight(1f) lets both lines share the free space.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerGray)
                Text(
                    text = "OR",
                    color = GrayText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerGray)
            }
            // ---- OR Divider End ----

            Spacer(modifier = Modifier.height(8.dp))

            // ---- Register Link Start ----
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Don't have an account?", fontSize = 16.sp, color = DarkText)
                // Changing currentScreen (in MainActivity.kt) opens the Register screen
                TextButton(onClick = { currentScreen = "register" }) {
                    Text(
                        text = "Register",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple
                    )
                }
            }
            // ---- Register Link End ----

            Spacer(modifier = Modifier.height(100.dp))
        }
        // ---- Form Column End ----
    }
    // ---- Background Box End ----
}
// ===== LoginScreen End =====


// ===== LoginScreen Preview Start =====
// Shows the screen in Android Studio's Split/Design view without running the app
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen()
    }
}
// ===== LoginScreen Preview End =====
