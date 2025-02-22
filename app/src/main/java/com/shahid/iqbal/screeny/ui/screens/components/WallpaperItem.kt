package com.shahid.iqbal.screeny.ui.screens.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable

@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    wallpaper: String,
    imageLoader: ImageLoader,
    isForApply: Boolean = false,
    getDrawable: ((Drawable) -> Unit)? = null,
    onWallpaperClick: (String) -> Unit = {}
) {

    var showShimmer by remember { mutableStateOf(true) }

    AsyncImage(
        model = wallpaper,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        imageLoader = imageLoader,
        onSuccess = { success ->
            showShimmer = false

            if (isForApply) {
                val drawable = success.result.drawable
                getDrawable?.invoke(drawable)
            }

        },
        modifier = modifier
            .fillMaxWidth()
            .background(
                shimmerBrush(targetValue = 1300f, showShimmer = showShimmer),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .height(200.dp)
            .noRippleClickable { onWallpaperClick(wallpaper) }

    )


}