package com.example.productexplorerapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// LAYER: PRESENTATION (reusable component)
// Why: this Composable has no ViewModel. It only gets data and click lambdas,
// so any screen can reuse it and it is easy to preview.

// ============================================================
// START: CategoryChips
// A row of chips: "All" + one chip per category.
// The selected chip is filled with the teal color.
// selectedCategory = null means "All" is selected.
// ============================================================
@Composable
fun CategoryChips(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    // LazyRow = a row that can scroll sideways
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        // ---------- "All" chip ----------
        item {
            CategoryChip(
                text = "All",
                isSelected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
            )
        }

        // ---------- One chip per category ----------
        items(categories) { category ->
            CategoryChip(
                // "men's clothing" -> "Men's clothing"
                text = category.replaceFirstChar { it.uppercase() },
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
            )
        }
    }
}
// ============================================================
// END: CategoryChips
// ============================================================


// ============================================================
// START: CategoryChip
// One chip. Used only by CategoryChips above, so it's private.
// ============================================================
@Composable
private fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    // ---------- Chip ----------
    // FilterChip is a Material 3 chip that can be selected or not
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(text) },
        // Teal background with white text when selected
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}
// ============================================================
// END: CategoryChip
// ============================================================


// ============================================================
// START: CategoryChipsPreview
// Shows the chips row in Android Studio's preview panel,
// with "Electronics" selected.
// ============================================================
@Preview(showBackground = true)
@Composable
fun CategoryChipsPreview() {
    CategoryChips(
        categories = listOf("electronics", "jewelery", "men's clothing"),
        selectedCategory = "electronics",
        onCategorySelected = {},
    )
}
// ============================================================
// END: CategoryChipsPreview
// ============================================================
