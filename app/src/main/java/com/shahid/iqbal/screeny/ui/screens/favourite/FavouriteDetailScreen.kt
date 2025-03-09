package com.shahid.iqbal.screeny.ui.screens.favourite

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.CommonWallpaperEntity
import com.shahid.iqbal.screeny.ui.screens.components.ActionButtons
import com.shahid.iqbal.screeny.ui.screens.wallpapers.ActionViewModel
import com.shahid.iqbal.screeny.ui.screens.wallpapers.WallpaperApplyDialog
import com.shahid.iqbal.screeny.utils.Extensions.debug
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavouriteDetailScreen(
    modifier: Modifier = Modifier,
    actionViewModel: ActionViewModel = koinViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    wallpaper: CommonWallpaperEntity,
    onBack: () -> Unit
) {

    BackHandler(onBack = onBack)

    var canShowDialog by remember { mutableStateOf(false) }
    var isFavourite by remember { mutableStateOf(true) }
    var wallpaperDrawable: Drawable? by remember { mutableStateOf(null) }
    val mContext = LocalContext.current

    Box(contentAlignment = Alignment.BottomCenter, modifier = modifier.fillMaxSize()) {

        AsyncImage(
            model = ImageRequest.Builder(mContext)
                .data(wallpaper.url)
                .listener(onSuccess = { _, res ->
                    wallpaperDrawable = res.drawable
                })
                .crossfade(false)
                .placeholderMemoryCacheKey("image-${wallpaper.url}")
                .memoryCacheKey("image-${wallpaper.url}")
                .build(),
            placeholder = null,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(
                        key = "image-${wallpaper.url}"
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxSize()
        )

        /** Action Buttons to download , apply or un-favourite wallpaper */
        ActionButtons(
            isFavourite = isFavourite,
            onApply = {
                "lkfklfsdhklhklsdfh".debug()
                canShowDialog = true
            }, onFavourite = {
                actionViewModel.addOrRemove(wallpaper = wallpaper)
                isFavourite = !isFavourite
            }, onDownload = {
                downloadWallpaper(actionViewModel, wallpaperUrl = wallpaper.url, mContext)
            })
    }


    /** Dialog display on apply button press*/
    if (canShowDialog) {
        if (wallpaperDrawable != null) {
            WallpaperApplyDialog(
                wallpaper = wallpaperDrawable,
                onDismissRequest = { canShowDialog = false })
        }
    }
}

/** download wallpaper from server */
private fun downloadWallpaper(
    actionViewModel: ActionViewModel, wallpaperUrl: String, context: Context
) {
    actionViewModel.downloadWallpaper(url = wallpaperUrl)
    Toast.makeText(context, context.getString(R.string.downloading), Toast.LENGTH_SHORT).show()
}