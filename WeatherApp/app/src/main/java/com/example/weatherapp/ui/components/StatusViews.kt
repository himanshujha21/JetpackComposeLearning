package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

// ============================================================
// START: LoadingView
// Full-screen spinner shown while data is loading.
// ============================================================
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // ---------- Spinner ----------
        CircularProgressIndicator()

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- "Loading…" text ----------
        Text(
            text = stringResource(R.string.loading),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
// ============================================================
// END: LoadingView
// ============================================================


// ============================================================
// START: ErrorView
// Full-screen error message with a Retry button.
// The parent decides what Retry does by passing `onRetry`.
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
        // ---------- Warning icon ----------
        Text(text = "⚠️", fontSize = 48.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Title ----------
        Text(
            text = stringResource(R.string.error_title),
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ---------- Error message ----------
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ---------- Retry button ----------
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}
// ============================================================
// END: ErrorView
// ============================================================


// ============================================================
// START: ErrorViewPreview
// ============================================================
@Preview(showBackground = true)
@Composable
private fun ErrorViewPreview() {
    WeatherAppTheme {
        ErrorView(message = "No internet connection. Please check your network.", onRetry = {})
    }
}
// ============================================================
// END: ErrorViewPreview
// ============================================================
