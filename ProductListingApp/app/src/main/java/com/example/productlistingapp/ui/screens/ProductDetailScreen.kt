package com.example.productlistingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.productlistingapp.model.Product
import com.example.productlistingapp.ui.CartViewModel
import com.example.productlistingapp.ui.ProductViewModel
import com.example.productlistingapp.ui.components.CartIconButton
import com.example.productlistingapp.ui.components.MessageView
import com.example.productlistingapp.util.formatPrice
import kotlinx.coroutines.launch

// ============================================================
// START: ProductDetailScreen
// Shows one product.
// NAVIGATION: AppNavigation reads the productId argument from the route
// and passes it here. We then find the product in the list the ViewModel
// already loaded. The screen gets onBackClick / onCartClick instead of the
// navController, so it doesn't need to know how navigation works.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
) {
    val uiState by productViewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()

    // find { } returns null if there is no product with this id
    val product = uiState.products.find { it.id == productId }

    val cartCount = cartItems.sumOf { it.quantity }

    // ---------- SIDE EFFECT: rememberCoroutineScope ----------
    // We want to show "Added to cart" when the user taps a button.
    // showSnackbar() is a suspend function, so it needs a coroutine.
    // rememberCoroutineScope() gives us a scope we can use inside click handlers.
    // It's tied to this screen: if the screen closes, its coroutines are cancelled.
    //
    // Why not LaunchedEffect? LaunchedEffect runs when the screen appears or a
    // key changes. Here the trigger is a click (an event), so we use a scope.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        // ---------- Top bar with back button and cart icon ----------
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = { CartIconButton(itemCount = cartCount, onClick = onCartClick) },
            )
        },
        // ---------- Place where the snackbar appears ----------
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        // ---------- Content ----------
        if (product == null) {
            MessageView(message = "Product not found", modifier = Modifier.padding(innerPadding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                // weight(1f) = take all the space above the bottom bar
                ProductInfo(
                    product = product,
                    modifier = Modifier.weight(1f),
                )

                AddToCartBar(
                    price = product.price,
                    onAddToCartClick = {
                        cartViewModel.addToCart(product)
                        // Start a coroutine from the click to show the snackbar
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
// END: ProductDetailScreen
// ============================================================


// ============================================================
// START: ProductInfo
// Big image, category, name, rating and description.
// verticalScroll lets the user scroll when the text is long.
// ============================================================
@Composable
private fun ProductInfo(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        // ---------- Product image ----------
        AsyncImage(
            model = product.image,
            contentDescription = product.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.White)
                .padding(24.dp),
        )

        Column(modifier = Modifier.padding(16.dp)) {
            // ---------- Category ----------
            Text(
                text = product.category.replaceFirstChar { it.uppercase() },
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ---------- Product name ----------
            Text(text = product.title, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            // ---------- Rating ----------
            Text(text = "⭐ ${product.rating.rate} (${product.rating.count} reviews)")

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // ---------- Description ----------
            Text(text = "Description", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description)
        }
    }
}
// ============================================================
// END: ProductInfo
// ============================================================


// ============================================================
// START: AddToCartBar
// The bar at the bottom: price on the left, "Add to cart" on the right.
// ============================================================
@Composable
private fun AddToCartBar(
    price: Double,
    onAddToCartClick: () -> Unit,
) {
    // Surface with a shadow so the bar stands out from the content
    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() // stay above the phone's navigation bar
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ---------- Price ----------
            Text(
                text = formatPrice(price),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )

            // ---------- Add to cart button ----------
            Button(onClick = onAddToCartClick) {
                Text("Add to cart")
            }
        }
    }
}
// ============================================================
// END: AddToCartBar
// ============================================================
