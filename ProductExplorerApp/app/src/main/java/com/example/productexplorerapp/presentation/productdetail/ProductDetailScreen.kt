package com.example.productexplorerapp.presentation.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.presentation.components.CartIconButton
import com.example.productexplorerapp.presentation.components.ErrorView
import com.example.productexplorerapp.presentation.components.LoadingView
import com.example.productexplorerapp.presentation.theme.ProductExplorerAppTheme
import com.example.productexplorerapp.presentation.util.formatPrice
import kotlinx.coroutines.launch

// LAYER: PRESENTATION (screen)
// Why: the screen gets its ViewModel, reads the state and passes data and
// clicks down to smaller Composables. No API or business logic lives here.

// ============================================================
// START: ProductDetailScreen
// Shows one product: big image, name, rating, price and description,
// with an "Add to cart" button at the bottom.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        // ---------- Top bar: back arrow + cart icon ----------
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    CartIconButton(
                        cartCount = cartItems.sumOf { it.quantity },
                        onClick = onCartClick,
                    )
                },
            )
        },

        // ---------- "Add to cart" bar, only after the product has loaded ----------
        bottomBar = {
            if (uiState.product != null) {
                AddToCartBar(
                    onAddToCartClick = {
                        viewModel.addToCart()
                        scope.launch {
                            snackbarHostState.showSnackbar("Added to cart")
                        }
                    },
                )
            }
        },

        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        // innerPadding = the space taken by the top bar and bottom bar
        Box(modifier = Modifier.padding(innerPadding)) {
            val product = uiState.product
            val errorMessage = uiState.errorMessage

            // ---------- Loading / error / product ----------
            if (uiState.isLoading) {
                LoadingView()
            } else if (errorMessage != null) {
                ErrorView(message = errorMessage, onRetry = { viewModel.loadProduct() })
            } else if (product != null) {
                ProductDetailContent(product = product)
            }
        }
    }
}
// ============================================================
// END: ProductDetailScreen
// ============================================================


// ============================================================
// START: ProductDetailContent
// The product information. It can be long, so the
// Column scrolls (verticalScroll).
// ============================================================
@Composable
private fun ProductDetailContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        // ---------- Big product image on a mint box ----------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(24.dp),
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Category ----------
        Text(
            text = product.category.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ---------- Product name ----------
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ---------- Rating ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xFFFFB300), // gold
                modifier = Modifier.size(18.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${product.rating} (${product.ratingCount} reviews)",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- Price ----------
        Text(
            text = formatPrice(product.price),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Description ----------
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
// ============================================================
// END: ProductDetailContent
// ============================================================


// ============================================================
// START: AddToCartBar
// The bar at the bottom of the detail screen with one big button.
// ============================================================
@Composable
private fun AddToCartBar(onAddToCartClick: () -> Unit) {
    // Surface gives the bar a background and a small shadow
    Surface(shadowElevation = 8.dp) {
        Button(
            onClick = onAddToCartClick,
            modifier = Modifier
                // Keep the button above the phone's gesture / navigation bar
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
        ) {
            Text("Add to cart")
        }
    }
}
// ============================================================
// END: AddToCartBar
// ============================================================


// ============================================================
// START: ProductDetailContentPreview
// Shows the product information in Android Studio's preview panel,
// with a fake product (the image is empty because there's no internet).
// ============================================================
@Preview(showBackground = true)
@Composable
fun ProductDetailContentPreview() {
    val product = Product(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use and walks in the forest.",
        category = "men's clothing",
        imageUrl = "",
        rating = 3.9,
        ratingCount = 120,
    )
    ProductExplorerAppTheme {
        ProductDetailContent(product = product)
    }
}
// ============================================================
// END: ProductDetailContentPreview
// ============================================================
