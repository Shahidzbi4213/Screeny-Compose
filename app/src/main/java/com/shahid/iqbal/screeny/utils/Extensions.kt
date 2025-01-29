package com.shahid.iqbal.screeny.utils

import android.app.Activity
import android.content.res.Configuration
import android.util.LayoutDirection
import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.text.layoutDirection
import java.util.Locale

object Extensions {


    fun Any?.debug(tag: String = "Logger") {
        Log.d(tag, "$this")
    }

    @ReadOnlyComposable
    @Composable
    internal fun Dp.toPx(): Float {
        return this.value * LocalDensity.current.density
    }

    @Stable
    fun Modifier.mirror(): Modifier {
        return if (Locale.getDefault().layoutDirection == LayoutDirection.RTL)
          scale(scaleX = -1f, scaleY = 1f)
        else
            this
    }


    @Composable
    fun Pulsating(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
        val infiniteTransition = rememberInfiniteTransition()

        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = pulseFraction,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(modifier = Modifier.scale(scale)) {
            content()
        }
    }
}