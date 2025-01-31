package com.shahid.iqbal.screeny.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

object ComponentHelpers {


    fun Modifier.noRippleClickable(
        onClick: () -> Unit
    ): Modifier = clickable(
        indication = null,
        interactionSource = NoRippleInteractionSource()
    ) {
        onClick()
    }
}