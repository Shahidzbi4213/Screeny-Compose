package com.shahid.iqbal.screeny.ui.screens.components

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import blur
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun BlurBg(
    wallpaperUrl: String, onLoaded: (Drawable) -> Unit
) {

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(wallpaperUrl)
            .crossfade(false)
            .size(Size.ORIGINAL).build()
    )

    LaunchedEffect(painter.state) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Success) {
            onLoaded(state.result.drawable)

            bitmap = state.result.drawable.toBitmap()
                .copy(Bitmap.Config.ARGB_8888, true)
                .blur(0.5f, 25)

        }
    }

    if (bitmap != null) {
        Image(
            bitmap!!.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f), Color.Transparent
                    )
                )
            )
    )
}