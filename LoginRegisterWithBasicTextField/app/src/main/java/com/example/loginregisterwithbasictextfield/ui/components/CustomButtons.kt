package com.example.loginregisterwithbasictextfield.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginregisterwithbasictextfield.R
import com.example.loginregisterwithbasictextfield.ui.theme.DarkText
import com.example.loginregisterwithbasictextfield.ui.theme.LightLavender
import com.example.loginregisterwithbasictextfield.ui.theme.PrimaryPurple

// ===================== PrimaryButton START =====================
// Full-width purple button used for Login, Register and Logout.
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryPurple,
            contentColor = Color.White,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
    ) {
        Text(text = text, fontSize = 17.sp, fontWeight = FontWeight.Bold)
    }
}
// ===================== PrimaryButton END =====================


// ===================== LinkText START =====================
// Clickable purple text, e.g. "Forgot Password?"
@Composable
fun LinkText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = PrimaryPurple,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp),
    )
}
// ===================== LinkText END =====================


// ===================== BottomTextLink START =====================
// e.g. "Don't have an account? Register"
@Composable
fun BottomTextLink(
    message: String,
    linkText: String,
    onLinkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // ---------- Row START ----------
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = message, color = DarkText, fontSize = 15.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = linkText,
            color = PrimaryPurple,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onLinkClick() },
        )
    }
    // ---------- Row END ----------
}
// ===================== BottomTextLink END =====================


// ===================== IconHeader START =====================
// Icon, title and subtitle at the top of the Login and Register screens
@Composable
fun IconHeader(
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    // ---------- Column START ----------
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // ---------- Icon box START ----------
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(LightLavender, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = PrimaryPurple,
                modifier = Modifier.size(44.dp),
            )
        }
        // ---------- Icon box END ----------

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = DarkText)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = subtitle, fontSize = 15.sp, color = DarkText, textAlign = TextAlign.Center)
    }
    // ---------- Column END ----------
}
// ===================== IconHeader END =====================


// ===================== Preview START =====================
@Preview(showBackground = true)
@Composable
private fun ButtonsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconHeader(icon = R.drawable.ic_lock, title = "Login", subtitle = "Welcome back!")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Login", onClick = {})
        LinkText(text = "Forgot Password?", onClick = {})
        BottomTextLink(message = "Don't have an account?", linkText = "Register", onLinkClick = {})
    }
}
// ===================== Preview END =====================
