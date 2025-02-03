package com.shahid.iqbal.screeny.ui.screens.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    isApplyEnable:Boolean,
    onBackPress: () -> Unit,
    onApply: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBackPress, interactionSource = NoRippleInteractionSource()
        ) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
        }


        Text(
            text = stringResource(id = R.string.language),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )

        TextButton(
            onClick = onApply,
            shape = RoundedCornerShape(10.dp),
            interactionSource = NoRippleInteractionSource(),
            modifier = Modifier.padding(horizontal = 10.dp),
            enabled = isApplyEnable
        ) {
            Text(
                text = stringResource(id = R.string.apply),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}
