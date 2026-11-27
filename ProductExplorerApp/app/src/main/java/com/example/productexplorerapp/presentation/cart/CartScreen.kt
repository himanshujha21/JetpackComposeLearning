package com.example.productexplorerapp.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.productexplorerapp.domain.model.CartItem
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.presentation.components.MessageView
import com.example.productexplorerapp.presentation.theme.ProductExplorerAppTheme
import com.example.productexplorerapp.presentation.util.formatPrice

// ============================================================
// START: CartScreen
// "My cart": the list of items with + / - buttons,
// and the total with a Checkout button at the bottom.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    // Add up the price of every line, e.g. (2 x $10) + (1 x $5) = $25
    val totalPrice = cartItems.sumOf { it.totalPrice }

    Scaffold(
        // ---------- Top bar ----------
        topBar = {
            TopAppBar(
                title = { Text("My cart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        // ---------- Total + Checkout, only when there is something in the cart ----------
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartSummary(
                    itemCount = cartItems.sumOf { it.quantity },
                    totalPrice = totalPrice,
                    onCheckoutClick = onCheckoutClick,
                )
            }
        },
    ) { innerPadding ->

        // ---------- Empty message or the list of items ----------
        if (cartItems.isEmpty()) {
            MessageView(
                message = "Your cart is empty",
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(cartItems) { item ->
                    CartItemRow(
                        cartItem = item,
                        onIncreaseClick = { viewModel.increaseQuantity(item.product.id) },
                        onDecreaseClick = { viewModel.decreaseQuantity(item.product.id) },
                        onRemoveClick = { viewModel.removeFromCart(item.product.id) },
                    )
                }
            }
        }
    }
}
// ============================================================
// END: CartScreen
// ============================================================


// ============================================================
// START: CartItemRow
// One line in the cart: image, name, price, and on the right
// the + button, the quantity and the - button (like the design).
// ============================================================
@Composable
fun CartItemRow(
    cartItem: CartItem,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // ---------- Small image ----------
        AsyncImage(
            model = cartItem.product.imageUrl,
            contentDescription = cartItem.product.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.width(12.dp))

        // ---------- Name, price and delete button ----------
        // weight(1f) = take all the space that's left between the image and the buttons
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cartItem.product.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = formatPrice(cartItem.totalPrice),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            IconButton(onClick = onRemoveClick, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove from cart",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }

        // ---------- + / quantity / - ----------
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FilledIconButton(onClick = onIncreaseClick, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Filled.Add, contentDescription = "Add one")
            }
            Text(
                text = cartItem.quantity.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp),
            )
            // The basic icon set has no "minus" icon, so we just write "-"
            FilledIconButton(onClick = onDecreaseClick, modifier = Modifier.size(28.dp)) {
                Text("-", fontWeight = FontWeight.Bold)
            }
        }
    }
}
// ============================================================
// END: CartItemRow
// ============================================================


// ============================================================
// START: CartSummary
// The bottom part of the cart: number of items, total price
// and the Checkout button.
// ============================================================
@Composable
private fun CartSummary(
    itemCount: Int,
    totalPrice: Double,
    onCheckoutClick: () -> Unit,
) {
    Surface(shadowElevation = 8.dp) {
        // navigationBarsPadding() keeps the content above the phone's gesture bar
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(16.dp),
        ) {
            // ---------- Number of items ----------
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Items", modifier = Modifier.weight(1f))
                Text(itemCount.toString())
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ---------- Total ----------
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = formatPrice(totalPrice),
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Checkout button ----------
            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Checkout")
            }
        }
    }
}
// ============================================================
// END: CartSummary
// ============================================================


// ============================================================
// START: CartItemRowPreview
// Shows one cart row in Android Studio's preview panel,
// with a fake product (no internet needed).
// ============================================================
@Preview(showBackground = true)
@Composable
fun CartItemRowPreview() {
    val product = Product(
        id = 1,
        title = "Mens Casual Premium Slim Fit T-Shirts",
        price = 22.3,
        description = "",
        category = "men's clothing",
        imageUrl = "",
        rating = 4.1,
        ratingCount = 259,
    )
    ProductExplorerAppTheme {
        CartItemRow(
            cartItem = CartItem(product = product, quantity = 2),
            onIncreaseClick = {},
            onDecreaseClick = {},
            onRemoveClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
// ============================================================
// END: CartItemRowPreview
// ============================================================
