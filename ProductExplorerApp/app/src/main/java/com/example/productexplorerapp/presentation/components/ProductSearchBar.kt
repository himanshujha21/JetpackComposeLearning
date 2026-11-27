package com.example.productexplorerapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ============================================================
// START: ProductSearchBar
// The search box at the top of the product list.
//
// STATE HOISTING: this Composable does NOT keep the text itself.
// It gets the text from outside (searchText) and reports every
// change back (onSearchTextChange). The ViewModel owns the text.
// ============================================================
@Composable
fun ProductSearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Search for any product name") },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),

        // ---------- Search icon on the left ----------
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = null)
        },

        // ---------- Clear (X) button on the right, only when there is text ----------
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChange("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear search")
                }
            }
        },
    )
}
// ============================================================
// END: ProductSearchBar
// ============================================================


// ============================================================
// START: ProductSearchBarPreview
// Shows the search bar in Android Studio's preview panel,
// with some text typed in, so the clear (X) button is visible.
// ============================================================
@Preview(showBackground = true)
@Composable
fun ProductSearchBarPreview() {
    ProductSearchBar(searchText = "shirt", onSearchTextChange = {})
}
// ============================================================
// END: ProductSearchBarPreview
// ============================================================
