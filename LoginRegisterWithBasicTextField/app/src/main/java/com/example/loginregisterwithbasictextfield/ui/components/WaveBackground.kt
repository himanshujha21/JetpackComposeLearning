package com.example.loginregisterwithbasictextfield.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import com.example.loginregisterwithbasictextfield.ui.theme.LightLavender
import com.example.loginregisterwithbasictextfield.ui.theme.ScreenBackground

// ===================== WaveBackground START =====================
// Draws the light purple waves at the top and bottom of the screen.
// Whatever we pass in `content` is drawn on top of the waves.
@Composable
fun WaveBackground(content: @Composable () -> Unit) {
    // ---------- Box START (waves behind, content in front) ----------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground),
    ) {
        // ---------- Canvas START (draws the waves) ----------
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // cubicTo draws a smooth curve. The first two points pull the line, the last is where it ends.
            val topWave = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, h * 0.16f)
                cubicTo(w * 0.20f, h * 0.18f, w * 0.30f, h * 0.08f, w * 0.55f, h * 0.11f)
                cubicTo(w * 0.75f, h * 0.13f, w * 0.85f, h * 0.12f, w, h * 0.06f)
                lineTo(w, 0f)
                close()
            }
            drawPath(topWave, LightLavender)

            val bottomWave = Path().apply {
                moveTo(0f, h)
                lineTo(0f, h * 0.94f)
                cubicTo(w * 0.25f, h * 0.91f, w * 0.45f, h * 0.97f, w * 0.70f, h * 0.94f)
                cubicTo(w * 0.85f, h * 0.92f, w * 0.92f, h * 0.91f, w, h * 0.91f)
                lineTo(w, h)
                close()
            }
            drawPath(bottomWave, LightLavender)
        }
        // ---------- Canvas END ----------

        // The screen's own UI goes here, on top of the waves
        content()
    }
    // ---------- Box END ----------
}
// ===================== WaveBackground END =====================


// ===================== Preview START =====================
@Preview(showBackground = true, heightDp = 640)
@Composable
private fun WaveBackgroundPreview() {
    WaveBackground {}
}
// ===================== Preview END =====================
