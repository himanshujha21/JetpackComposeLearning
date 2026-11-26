package com.example.productlistingapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// ============================================================
// START: ProductSearchBar
// The search box at the top of the product list.
// The text is kept in the ViewModel, so we get it as a parameter
// and send every change back with onTextChange ("state hoisting").
// ============================================================
@Composable
fun ProductSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        placeholder = { Text("Search products") },
        singleLine = true,
        shape = RoundedCornerShape(50), // round "pill" shape

        // ---------- Search icon (left) ----------
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },

        // ---------- Clear button (right) ----------
        // Only shown when there is some text to clear
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChange("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear search")
                }
            }
        },
    )
}
// ============================================================
// END: ProductSearchBar
// ============================================================
