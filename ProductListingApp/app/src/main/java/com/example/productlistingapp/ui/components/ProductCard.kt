package com.example.productlistingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.productlistingapp.model.Product
import com.example.productlistingapp.model.Rating
import com.example.productlistingapp.util.formatPrice

// ============================================================
// START: ProductCard
// One item in the product grid: image, name, rating and price.
// It's reusable: it only needs a product and what to do on click.
// ============================================================
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Card(onClick = ...) makes the whole card clickable
    Card(onClick = onClick, modifier = modifier) {
        Column {
            // ---------- Product image ----------
            // AsyncImage (from Coil) downloads the image from the URL and shows it
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // square
                    .background(Color.White)
                    .padding(12.dp),
            )

            Column(modifier = Modifier.padding(12.dp)) {
                // ---------- Product name ----------
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    minLines = 2, // so all cards have the same height
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ---------- Rating ----------
                Text(
                    text = "⭐ ${product.rating.rate} (${product.rating.count})",
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ---------- Price ----------
                Text(
                    text = formatPrice(product.price),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
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
        image = "",
        rating = Rating(rate = 3.9, count = 120),
    )
    ProductCard(product = product, onClick = {}, modifier = Modifier.width(180.dp))
}
// ============================================================
// END: ProductCardPreview
// ============================================================
