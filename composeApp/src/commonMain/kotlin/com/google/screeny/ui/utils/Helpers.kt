package com.google.screeny.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

object Helpers {


    fun Modifier.noRippleClickable(
        onClick: () -> Unit
    ): Modifier = clickable(
        indication = null,
        interactionSource = NoRippleInteractionSource()
    ) {
        onClick()
    }
}