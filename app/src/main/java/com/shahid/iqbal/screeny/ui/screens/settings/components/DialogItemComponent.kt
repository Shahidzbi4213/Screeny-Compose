package com.shahid.iqbal.screeny.ui.screens.settings.components;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .noRippleClickable { onClick() }
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text, modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(10.dp))
        RadioButton(selected = isSelected, onClick = null)
    }

}