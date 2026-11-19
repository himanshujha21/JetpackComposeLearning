package com.example.loginregisterwithbasictextfield.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginregisterwithbasictextfield.R
import com.example.loginregisterwithbasictextfield.ui.theme.DarkText
import com.example.loginregisterwithbasictextfield.ui.theme.ErrorRed
import com.example.loginregisterwithbasictextfield.ui.theme.FieldBackground
import com.example.loginregisterwithbasictextfield.ui.theme.FieldBorder
import com.example.loginregisterwithbasictextfield.ui.theme.GreyText
import com.example.loginregisterwithbasictextfield.ui.theme.PrimaryPurple

// ===================== CustomTextField START =====================
// BasicTextField only draws the text. It has no border, icon or hint,
// so we draw those ourselves inside decorationBox.
// Set isPassword = true to hide the text and show the eye icon.
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onDone: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    val borderColor = when {
        errorMessage != null -> ErrorRed
        isFocused -> PrimaryPurple
        else -> FieldBorder
    }
    val shape = RoundedCornerShape(16.dp)

    // ---------- Column START (field + error message) ----------
    Column(modifier = modifier.fillMaxWidth()) {

        // ---------- BasicTextField START ----------
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp, color = DarkText),
            cursorBrush = SolidColor(PrimaryPurple),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            // Show dots instead of the real text when it's a hidden password
            visualTransformation = if (isPassword && !showPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            decorationBox = { innerTextField ->

                // ---------- Row START (background, border, icons) ----------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(FieldBackground, shape)
                        .border(1.dp, borderColor, shape)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Icon at the start of the field
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = if (isFocused) PrimaryPurple else DarkText,
                        modifier = Modifier.size(22.dp),
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // ---------- Box START (hint + typed text) ----------
                    Box(modifier = Modifier.weight(1f)) {
                        // Show the hint only while the field is empty
                        if (value.isEmpty()) {
                            Text(text = hint, fontSize = 16.sp, color = GreyText)
                        }
                        // innerTextField is the actual editable text
                        innerTextField()
                    }
                    // ---------- Box END ----------

                    // Eye icon, only for password fields
                    if (isPassword) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(
                                if (showPassword) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                            ),
                            contentDescription = "Show or hide password",
                            tint = DarkText,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { showPassword = !showPassword },
                        )
                    }
                }
                // ---------- Row END ----------
            },
        )
        // ---------- BasicTextField END ----------

        // Error message below the field
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = 13.sp,
                color = ErrorRed,
                modifier = Modifier.padding(start = 18.dp, top = 4.dp),
            )
        }
    }
    // ---------- Column END ----------
}
// ===================== CustomTextField END =====================


// ===================== Preview START =====================
@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        CustomTextField(
            value = "",
            onValueChange = {},
            hint = "Email",
            icon = R.drawable.ic_email,
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomTextField(
            value = "abc",
            onValueChange = {},
            hint = "Password",
            icon = R.drawable.ic_lock_outline,
            isPassword = true,
            errorMessage = "Password must be at least 6 characters",
        )
    }
}
// ===================== Preview END =====================
