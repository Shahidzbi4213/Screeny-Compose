package com.shahid.iqbal.screeny.ui.screens.settings;

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource
import com.shahid.iqbal.screeny.utils.Extensions.mirror

/**
 * Created by Shahid Iqbal on 28/1/25.
 */


@Composable
fun GeneralItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int, description: String,
    @DrawableRes icon: Int, onClick: () -> Unit
) {

    Column {

        Card(
            onClick = onClick,
            interactionSource = NoRippleInteractionSource(),
            modifier = modifier
                .fillMaxWidth(0.95f)
                .height(70.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f),
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    fontFamily = screenyFontFamily
                )

                Text(
                    description, style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null, modifier = Modifier.mirror().rotate(90f)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }


}