package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

// ============================================================
// START: CitySearchBar
// Text field where the user types a city name.
// The search starts when they press "Search" or the search key on the keyboard.
//
// This Composable does not keep its own text. The parent passes in
// `text` and gets changes back through `onTextChange` (state hoisting).
// ============================================================
@Composable
fun CitySearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Lets us hide the on-screen keyboard after searching
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.search_hint)) },

        // ---------- Icon on the left ----------
        leadingIcon = { Text("🔍") },

        // ---------- Search button on the right ----------
        trailingIcon = {
            TextButton(
                onClick = {
                    keyboardController?.hide()
                    onSearch()
                },
            ) {
                Text(stringResource(R.string.search))
            }
        },

        // ---------- Look and keyboard ----------
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        // Shows a "search" key on the keyboard instead of "enter"
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch()
            },
        ),
    )
}
// ============================================================
// END: CitySearchBar
// ============================================================


// ============================================================
// START: CitySearchBarPreview
// ============================================================
@Preview(showBackground = true)
@Composable
private fun CitySearchBarPreview() {
    WeatherAppTheme {
        CitySearchBar(text = "Mumbai", onTextChange = {}, onSearch = {})
    }
}
// ============================================================
// END: CitySearchBarPreview
// ============================================================
