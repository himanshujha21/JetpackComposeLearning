package com.example.productexplorerapp.presentation.productlist

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.presentation.components.CartIconButton
import com.example.productexplorerapp.presentation.components.CategoryChips
import com.example.productexplorerapp.presentation.components.ErrorView
import com.example.productexplorerapp.presentation.components.LoadingView
import com.example.productexplorerapp.presentation.components.MessageView
import com.example.productexplorerapp.presentation.components.ProductCard
import com.example.productexplorerapp.presentation.components.ProductSearchBar
import com.example.productexplorerapp.presentation.components.SortMenu
import kotlinx.coroutines.launch

// LAYER: PRESENTATION (screen)
// Why: the screen gets its ViewModel, reads the state and passes data and
// clicks down to smaller Composables. No API or business logic lives here.

// ============================================================
// START: ProductListScreen
// The first screen: search bar, category chips, sort menu
// and a grid of products.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class) // TopAppBar is still marked experimental
@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    viewModel: ProductListViewModel = hiltViewModel(),
) {
    // Read the state from the ViewModel. The screen redraws when it changes.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    // For the "Added to cart" message.
    // showSnackbar() is a suspend function, so we need a coroutine scope to call it.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        // ---------- Top bar with the cart icon ----------
        topBar = {
            TopAppBar(
                title = { Text("Product Explorer") },
                actions = {
                    CartIconButton(
                        cartCount = cartItems.sumOf { it.quantity },
                        onClick = onCartClick,
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        // innerPadding = the space taken by the top bar
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            // ---------- Search bar ----------
            ProductSearchBar(
                searchText = uiState.searchText,
                onSearchTextChange = { text -> viewModel.onSearchTextChange(text) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Category chips ----------
            CategoryChips(
                categories = uiState.categories,
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { category -> viewModel.onCategorySelected(category) },
            )

            // ---------- "PRODUCTS" title + sort menu ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "PRODUCTS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f),
                )
                SortMenu(
                    selectedOption = uiState.sortOption,
                    onOptionSelected = { option -> viewModel.onSortOptionSelected(option) },
                )
            }

            // ---------- Loading / error / empty / grid ----------
            // Only one of these is shown at a time
            val errorMessage = uiState.errorMessage

            if (uiState.isLoading) {
                LoadingView()
            } else if (errorMessage != null) {
                ErrorView(message = errorMessage, onRetry = { viewModel.loadProducts() })
            } else if (uiState.products.isEmpty()) {
                MessageView(message = "No products found")
            } else {
                ProductGrid(
                    products = uiState.products,
                    onProductClick = onProductClick,
                    onAddToCartClick = { product ->
                        viewModel.addToCart(product)
                        scope.launch {
                            snackbarHostState.showSnackbar("Added to cart")
                        }
                    },
                )
            }
        }
    }
}
// ============================================================
// END: ProductListScreen
// ============================================================


// ============================================================
// START: ProductGrid
// Two columns of ProductCards.
// LazyVerticalGrid only creates the cards that are on screen,
// so scrolling stays smooth.
// ============================================================
@Composable
private fun ProductGrid(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    onAddToCartClick: (Product) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // ---------- One ProductCard per product ----------
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { onProductClick(product.id) },
                onAddToCartClick = { onAddToCartClick(product) },
            )
        }
    }
}
// ============================================================
// END: ProductGrid
// ============================================================
