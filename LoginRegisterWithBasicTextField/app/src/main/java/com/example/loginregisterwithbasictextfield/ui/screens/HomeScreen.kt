package com.example.loginregisterwithbasictextfield.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginregisterwithbasictextfield.R
import com.example.loginregisterwithbasictextfield.ui.components.PrimaryButton
import com.example.loginregisterwithbasictextfield.ui.components.WaveBackground
import com.example.loginregisterwithbasictextfield.ui.theme.DarkText

// ===================== HomeScreen START =====================
// Shown after a successful login or registration.
@Composable
fun HomeScreen(
    userName: String,
    onLogoutClick: () -> Unit,
) {
    // ---------- WaveBackground START ----------
    WaveBackground {

        // ---------- Column START ----------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Greeting text
            Text(
                text = stringResource(R.string.home_greeting, userName),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Logout button
            PrimaryButton(text = stringResource(R.string.logout_button), onClick = onLogoutClick)
        }
        // ---------- Column END ----------
    }
    // ---------- WaveBackground END ----------
}
// ===================== HomeScreen END =====================


// ===================== Preview START =====================
@Preview(showBackground = true, heightDp = 640)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(userName = "John", onLogoutClick = {})
}
// ===================== Preview END =====================
