package com.example.productlistingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// ============================================================
// START: LoadingView
// A spinner in the middle of the screen while products load.
// ============================================================
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}
// ============================================================
// END: LoadingView
// ============================================================


// ============================================================
// START: ErrorView
// An error message with a Retry button.
// The screen that uses it decides what Retry does (onRetry).
// ============================================================
@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // ---------- Error message ----------
        Text(text = message, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Retry button ----------
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
// ============================================================
// END: ErrorView
// ============================================================


// ============================================================
// START: MessageView
// A simple text in the middle of the screen,
// e.g. "No products found" or "Your cart is empty".
// ============================================================
@Composable
fun MessageView(
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message, textAlign = TextAlign.Center)
    }
}
// ============================================================
// END: MessageView
// ============================================================
