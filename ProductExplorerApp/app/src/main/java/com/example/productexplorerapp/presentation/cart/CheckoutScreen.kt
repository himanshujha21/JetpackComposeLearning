package com.example.productexplorerapp.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productexplorerapp.presentation.theme.ProductExplorerAppTheme
import com.example.productexplorerapp.presentation.util.formatPrice

// LAYER: PRESENTATION (screen)
// Why: the screen gets its ViewModel, reads the state and passes data and
// clicks down to smaller Composables. No API or business logic lives here.

// The payment options shown on the checkout screen
private val paymentMethods = listOf("Credit card", "PayPal", "Cash on delivery")

// ============================================================
// START: CheckoutScreen
// Pick a payment method, check the order and tap "Make payment".
// This is only a demo: no real payment happens, the cart is
// just emptied and a "Order placed" dialog is shown.
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBackClick: () -> Unit,
    onOrderPlaced: () -> Unit,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val totalPrice = cartItems.sumOf { it.totalPrice }

    // Screen-only state, so it lives here and not in the ViewModel.
    // rememberSaveable (instead of remember) also keeps the value after a screen rotation.
    var selectedPayment by rememberSaveable { mutableStateOf(paymentMethods.first()) }
    var showOrderPlacedDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        // ---------- Top bar ----------
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        // ---------- Total + Make payment button ----------
        bottomBar = {
            // Hide it after the order is placed (the cart is empty then)
            if (cartItems.isNotEmpty()) {
                PaymentBar(
                    totalPrice = totalPrice,
                    onMakePaymentClick = {
                        viewModel.placeOrder()
                        showOrderPlacedDialog = true
                    },
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            // ---------- Saved card (only when "Credit card" is picked) ----------
            if (selectedPayment == "Credit card") {
                SavedCard()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ---------- Payment methods ----------
            Text(
                text = "Choose payment",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))

            paymentMethods.forEach { method ->
                PaymentMethodRow(
                    name = method,
                    isSelected = method == selectedPayment,
                    onClick = { selectedPayment = method },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- Order summary ----------
            Text(
                text = "Order summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))

            cartItems.forEach { item ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "${item.quantity} x ${item.product.title}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                    Text(text = formatPrice(item.totalPrice))
                }
            }
        }
    }

    // ---------- "Order placed" dialog ----------
    // The dialog is shown on top of the screen while showOrderPlacedDialog is true.
    // Closing it takes the user back to the product list.
    if (showOrderPlacedDialog) {
        AlertDialog(
            onDismissRequest = onOrderPlaced,
            title = { Text("Order placed") },
            text = { Text("Thank you! Your order was paid with $selectedPayment.") },
            confirmButton = {
                TextButton(onClick = onOrderPlaced) {
                    Text("Continue shopping")
                }
            },
        )
    }
}
// ============================================================
// END: CheckoutScreen
// ============================================================


// ============================================================
// START: SavedCard
// A teal "credit card" with demo data, like in the design.
// ============================================================
@Composable
private fun SavedCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp),
    ) {
        val textColor = MaterialTheme.colorScheme.onPrimary

        // ---------- Card title ----------
        Text(text = "Credit Card", color = textColor, fontWeight = FontWeight.Bold)

        // Pushes the rest to the bottom of the card
        Spacer(modifier = Modifier.weight(1f))

        // ---------- Card number (demo) ----------
        Text(
            text = "**** **** **** 4242",
            color = textColor,
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- Card owner (demo) ----------
        Text(text = "Owner", color = textColor, style = MaterialTheme.typography.labelSmall)
        Text(text = "J. Doe", color = textColor, fontWeight = FontWeight.Bold)
    }
}
// ============================================================
// END: SavedCard
// ============================================================


// ============================================================
// START: PaymentMethodRow
// A radio button with the name of the payment method.
// The whole row is clickable, not only the small circle.
// ============================================================
@Composable
private fun PaymentMethodRow(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = name)
    }
}
// ============================================================
// END: PaymentMethodRow
// ============================================================


// ============================================================
// START: PaymentBar
// The bottom bar of the checkout: total and "Make payment".
// ============================================================
@Composable
private fun PaymentBar(
    totalPrice: Double,
    onMakePaymentClick: () -> Unit,
) {
    Surface(shadowElevation = 8.dp) {
        // navigationBarsPadding() keeps the content above the phone's gesture bar
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(16.dp),
        ) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Total ----------
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Total", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(text = formatPrice(totalPrice), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ---------- Make payment button ----------
            Button(
                onClick = onMakePaymentClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text("Make payment")
            }
        }
    }
}
// ============================================================
// END: PaymentBar
// ============================================================


// ============================================================
// START: SavedCardPreview
// Shows the teal credit card in Android Studio's preview panel.
// ============================================================
@Preview(showBackground = true)
@Composable
fun SavedCardPreview() {
    ProductExplorerAppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SavedCard()
        }
    }
}
// ============================================================
// END: SavedCardPreview
// ============================================================
