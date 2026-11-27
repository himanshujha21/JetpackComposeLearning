package com.example.productexplorerapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.productexplorerapp.domain.model.Product
import com.example.productexplorerapp.presentation.theme.ProductExplorerAppTheme
import com.example.productexplorerapp.presentation.util.formatPrice

// LAYER: PRESENTATION (reusable component)
// Why: this Composable has no ViewModel. It only gets data and click lambdas,
// so any screen can reuse it and it is easy to preview.

// ============================================================
// START: ProductCard
// One product in the grid: image on a mint box, name, price
// and a small cart button (like the Shopwise design).
// Tapping the card opens the detail screen.
// ============================================================
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(4.dp),
    ) {
        // ---------- Product image ----------
        // The mint box behind the image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // a square
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp),
        ) {
            // AsyncImage (from Coil) downloads the image from the URL and shows it.
            // The Fake Store photos have a white background, so we put them on a
            // white rounded box inside the mint one.
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(8.dp),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ---------- Product name ----------
        Text(
            text = product.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            minLines = 2, // so all cards have the same height
            overflow = TextOverflow.Ellipsis,
        )

        // ---------- Price + add to cart button ----------
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatPrice(product.price),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                // weight(1f) takes all the free space, pushing the button to the right
                modifier = Modifier.weight(1f),
            )

            // A thin teal border around the button, like the design
            OutlinedIconButton(
                onClick = onAddToCartClick,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Add to cart",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
// ============================================================
// END: ProductCard
// ============================================================


// ============================================================
// START: ProductCardPreview
// Shows the card in Android Studio's preview panel.
// (The image is empty here because the preview has no internet.)
// ============================================================
@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    val product = Product(
        id = 1,
        title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price = 109.95,
        description = "Your perfect pack for everyday use.",
        category = "men's clothing",
        imageUrl = "",
        rating = 3.9,
        ratingCount = 120,
    )
    ProductExplorerAppTheme {
        ProductCard(
            product = product,
            onClick = {},
            onAddToCartClick = {},
            modifier = Modifier.width(180.dp),
        )
    }
}
// ============================================================
// END: ProductCardPreview
// ============================================================
