package com.example.productlistingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productlistingapp.model.Product
import com.example.productlistingapp.ui.CartViewModel
import com.example.productlistingapp.ui.ProductUiState
import com.example.productlistingapp.ui.ProductViewModel
import com.example.productlistingapp.ui.components.CartIconButton
import com.example.productlistingapp.ui.components.CategoryFilterRow
import com.example.productlistingapp.ui.components.ErrorView
import com.example.productlistingapp.ui.components.LoadingView
import com.example.productlistingapp.ui.components.MessageView
import com.example.productlistingapp.ui.components.ProductCard
import com.example.productlistingapp.ui.components.ProductSearchBar
import com.example.productlistingapp.ui.components.SortMenu

// ============================================================
// START: ProductListScreen
// The first screen of the app.
// Shows a loader, an error, or the list of products.
// NAVIGATION: when a product is tapped we just call onProductClick(id).
// AppNavigation turns that into navigate(ProductDetailRoute(id)).
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
) {
    // Read the state from the ViewModels.
    // When the state changes, this screen redraws by itself.
    val uiState by productViewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()

    // Total number of items in the cart, for the badge
    val cartCount = cartItems.sumOf { it.quantity }

    // Scaffold gives us a place for the top bar and the content
    Scaffold(
        // ---------- Top bar ----------
        topBar = {
            TopAppBar(
                title = { Text("Shop") },
                actions = { CartIconButton(itemCount = cartCount, onClick = onCartClick) },
            )
        },
    ) { innerPadding ->
        // ---------- Content ----------
        // innerPadding = the space used by the top bar, so our content isn't hidden under it
        val errorMessage = uiState.errorMessage

        if (uiState.isLoading) {
            LoadingView(modifier = Modifier.padding(innerPadding))
        } else if (errorMessage != null) {
            ErrorView(
                message = errorMessage,
                onRetry = { productViewModel.loadProducts() },
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            ProductListContent(
                uiState = uiState,
                productViewModel = productViewModel,
                onProductClick = onProductClick,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
// ============================================================
// END: ProductListScreen
// ============================================================


// ============================================================
// START: ProductListContent
// What we show after the products are loaded:
// search box, category chips, sort menu and the product grid.
// ============================================================
@Composable
private fun ProductListContent(
    uiState: ProductUiState,
    productViewModel: ProductViewModel,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val products = uiState.visibleProducts

    Column(modifier = modifier) {
        // ---------- Search box ----------
        ProductSearchBar(
            text = uiState.searchText,
            onTextChange = { productViewModel.onSearchTextChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- Category chips ----------
        CategoryFilterRow(
            categories = uiState.categories,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = { productViewModel.selectCategory(it) },
        )

        // ---------- Product count + sort menu ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // weight(1f) takes the free space and pushes the sort menu to the right
            Text(text = "${products.size} products", modifier = Modifier.weight(1f))
            SortMenu(
                selectedOption = uiState.sortOption,
                onOptionSelected = { productViewModel.selectSortOption(it) },
            )
        }

        // ---------- Product grid (or a message if nothing matches) ----------
        if (products.isEmpty()) {
            MessageView(message = "No products found")
        } else {
            ProductGrid(products = products, onProductClick = onProductClick)
        }
    }
}
// ============================================================
// END: ProductListContent
// ============================================================


// ============================================================
// START: ProductGrid
// A grid with 2 columns.
// Like LazyColumn, LazyVerticalGrid only draws the items you can see,
// so it stays fast even with many products.
// ============================================================
@Composable
private fun ProductGrid(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        // key = { it.id } helps Compose keep track of each item when the list changes
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                onClick = { onProductClick(product.id) },
            )
        }
    }
}
// ============================================================
// END: ProductGrid
// ============================================================
