package com.example.productlistingapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.productlistingapp.ui.SortOption

// ============================================================
// START: SortMenu
// A button that opens a dropdown with the sort options.
// ============================================================
@Composable
fun SortMenu(
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit,
) {
    // Is the menu open? Only this button needs to know, so the state lives here.
    // remember { } keeps the value when the screen redraws.
    var expanded by remember { mutableStateOf(false) }

    // The Box makes the menu open right under the button
    Box {
        // ---------- Button with the current option ----------
        TextButton(onClick = { expanded = true }) {
            Text(selectedOption.label)
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        }

        // ---------- Dropdown menu ----------
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // SortOption.entries = all the values of the enum
            SortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}
// ============================================================
// END: SortMenu
// ============================================================
