package com.shahid.iqbal.screeny.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable

/**
 * Creation of Saif Ullah Shah on 3/2/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateUsDialog(
    modifier: Modifier,
    onRating: (Float) -> Unit,
    onDismiss: () -> Unit,
) {

    var userRating by remember { mutableFloatStateOf(4f) }

    ModalBottomSheet(
        modifier = modifier,
        containerColor = White,
        dragHandle = {},
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 25.dp, top = 25.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Black,
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                maxLines = 1,
                text = stringResource(R.string.rate_us)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Black,
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                maxLines = 1,
                text = stringResource(R.string.your_opinion_matters_to_us)
            )
            Spacer(modifier = Modifier.height(30.dp))
            RatingBar(
                userRating = userRating,
                onRatingChanged = {
                    userRating = it
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    onDismiss()
                    onRating(userRating + 1)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 25.dp)
                    .noRippleClickable {},
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = White,
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1,
                    text = stringResource(R.string.submit)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}