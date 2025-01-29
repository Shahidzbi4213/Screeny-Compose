package com.shahid.iqbal.screeny.ui.screens.settings.components;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource

/**
 * Created by Shahid Iqbal on 29/1/25.
 */

@Composable
fun DialogComponentItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        interactionSource = NoRippleInteractionSource(),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text, modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                style = MaterialTheme.typography.titleMedium
            )
            RadioButton(selected = isSelected, onClick = null)
        }

    }

}