package com.shahid.iqbal.screeny.ui.screens.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable

/**
 * Creation of Saif Ullah Shah on 3/2/25.
 */

@Composable
fun RatingBar(
    userRating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val starsCount: Int = 5
    val density = LocalDensity.current.density
    val starSize = (24f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(starsCount) {
            val isSelected = (it <= userRating)
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Gray
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .noRippleClickable {
                        onRatingChanged(it.toFloat())
                    }
                    .width(starSize)
                    .height(starSize)
            )

            if (it < starsCount) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}