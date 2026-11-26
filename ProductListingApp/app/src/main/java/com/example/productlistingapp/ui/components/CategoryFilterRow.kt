package com.example.productlistingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// ============================================================
// START: CategoryFilterRow
// A row of chips you can scroll sideways: All, Electronics, Jewelery...
// selectedCategory = null means "All" is selected.
// ============================================================
@Composable
fun CategoryFilterRow(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
) {
    // LazyRow is like LazyColumn, but it scrolls sideways
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // ---------- "All" chip ----------
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") },
            )
        }

        // ---------- One chip for each category ----------
        items(categories) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                // "men's clothing" -> "Men's clothing"
                label = { Text(category.replaceFirstChar { it.uppercase() }) },
            )
        }
    }
}
// ============================================================
// END: CategoryFilterRow
// ============================================================
