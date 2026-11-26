package com.example.productlistingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.productlistingapp.model.CartItem
import com.example.productlistingapp.ui.CartViewModel
import com.example.productlistingapp.ui.components.MessageView
import com.example.productlistingapp.util.formatPrice

// ============================================================
// START: CartScreen
// Shows the items in the cart, the total price and a Checkout button.
// NAVIGATION: this screen doesn't know about the navController.
// It only gets onBackClick, and AppNavigation decides what "back" does.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    onBackClick: () -> Unit,
) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()
    val message by cartViewModel.message.collectAsStateWithLifecycle()

    // Add up price x quantity for every item
    val totalPrice = cartItems.sumOf { it.product.price * it.quantity }

    // ---------- SIDE EFFECT: LaunchedEffect ----------
    // Showing a Snackbar is a "side effect": it does something outside of drawing the UI.
    // We can't call showSnackbar() directly here, because this function runs again
    // on every recomposition (and showSnackbar() is a suspend function).
    //
    // LaunchedEffect(message) starts a coroutine that runs:
    //   - once when the screen appears, and
    //   - again every time `message` (the "key") changes.
    // So when checkout() sets a message, this block runs and shows it.
    // If the screen is closed, the coroutine is cancelled automatically.
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        // ?.let runs only when message is not null
        message?.let {
            snackbarHostState.showSnackbar(it)
            // Tell the ViewModel we showed it, so it isn't shown again
            cartViewModel.messageShown()
        }
    }

    Scaffold(
        // ---------- Top bar with back button ----------
        topBar = {
            TopAppBar(
                title = { Text("Your cart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        // ---------- Place where the snackbar appears ----------
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        // ---------- Content ----------
        if (cartItems.isEmpty()) {
            MessageView(message = "Your cart is empty", modifier = Modifier.padding(innerPadding))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                // ---------- List of cart items ----------
                // weight(1f) = take all the space above the total bar
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(cartItems, key = { it.product.id }) { item ->
                        CartItemRow(
                            item = item,
                            onIncrease = { cartViewModel.increaseQuantity(item.product.id) },
                            onDecrease = { cartViewModel.decreaseQuantity(item.product.id) },
                            onRemove = { cartViewModel.removeFromCart(item.product.id) },
                        )
                    }
                }

                // ---------- Total + Checkout ----------
                CartTotalBar(
                    totalPrice = totalPrice,
                    // A click is an event, not a side effect: it runs only when the user taps
                    onCheckoutClick = { cartViewModel.checkout() },
                )
            }
        }
    }
}
// ============================================================
// END: CartScreen
// ============================================================


// ============================================================
// START: CartItemRow
// One item in the cart: image, name, price,
// the - quantity + buttons and a delete button.
// ============================================================
@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // ---------- Small product image ----------
            AsyncImage(
                model = item.product.image,
                contentDescription = item.product.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(72.dp)
                    .background(Color.White)
                    .padding(8.dp),
            )

            Spacer(modifier = Modifier.width(12.dp))

            // weight(1f) = use the space left between the image and the delete button
            Column(modifier = Modifier.weight(1f)) {
                // ---------- Name and price ----------
                Text(
                    text = item.product.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = formatPrice(item.product.price),
                    fontWeight = FontWeight.Bold,
                )

                // ---------- Quantity: - 2 + ----------
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(onClick = onDecrease) { Text("-") }
                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                    )
                    OutlinedButton(onClick = onIncrease) { Text("+") }
                }
            }

            // ---------- Delete button ----------
            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Delete, contentDescription = "Remove")
            }
        }
    }
}
// ============================================================
// END: CartItemRow
// ============================================================


// ============================================================
// START: CartTotalBar
// The bar at the bottom with the total price and the Checkout button.
// ============================================================
@Composable
private fun CartTotalBar(
    totalPrice: Double,
    onCheckoutClick: () -> Unit,
) {
    Surface(shadowElevation = 8.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp),
        ) {
            // ---------- Total price ----------
            Row {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = formatPrice(totalPrice),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Checkout button ----------
            Button(
                onClick = onCheckoutClick,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Checkout")
            }
        }
    }
}
// ============================================================
// END: CartTotalBar
// ============================================================
