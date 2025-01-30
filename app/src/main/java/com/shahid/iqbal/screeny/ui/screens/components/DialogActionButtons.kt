package com.shahid.iqbal.screeny.ui.screens.components;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource

/**
 * Created by Shahid Iqbal on 30/1/25.
 */

@Composable
fun DialogActionButtons(
    modifier: Modifier = Modifier, successButtonText: String, cancelButtonText: String, onSuccess: () -> Unit, onCancel: () -> Unit
) {


    Row(
        modifier = modifier.padding(horizontal = 10.dp), horizontalArrangement = Arrangement.Absolute.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        SingleActionButton(modifier = Modifier, text = cancelButtonText, isCancelButton = true, onClick = onCancel)
        SingleActionButton(modifier = Modifier, text = successButtonText, onClick = onSuccess)
    }
}


@Composable
private fun RowScope.SingleActionButton(
    modifier: Modifier = Modifier, text: String, isCancelButton: Boolean = false, onClick: () -> Unit
) {
    val buttonColors = if (isCancelButton) {
        ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onSurface
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }

    val buttonModifier = modifier
        .weight(1f)
        .height(40.dp)

    if (isCancelButton) {
        OutlinedButton(
            onClick = onClick, modifier = buttonModifier, colors = buttonColors,
            interactionSource = NoRippleInteractionSource()
        ) {
            Text(text = text)
        }
    } else {
        Button(
            onClick = onClick, modifier = buttonModifier, colors = buttonColors,
            interactionSource = NoRippleInteractionSource()
        ) {
            Text(text = text)
        }
    }
}
