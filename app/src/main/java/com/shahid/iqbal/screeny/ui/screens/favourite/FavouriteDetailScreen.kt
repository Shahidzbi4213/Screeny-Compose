package com.shahid.iqbal.screeny.ui.screens.favourite

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.CommonWallpaperEntity
import com.shahid.iqbal.screeny.ui.screens.components.ActionButtons
import com.shahid.iqbal.screeny.ui.screens.wallpapers.ActionViewModel
import com.shahid.iqbal.screeny.ui.screens.wallpapers.UiEvents
import com.shahid.iqbal.screeny.ui.screens.wallpapers.WallpaperApplyDialog
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavouriteDetailScreen(
    modifier: Modifier = Modifier,
    actionViewModel: ActionViewModel = koinViewModel(),
    detailVm: FavouriteDetailScreenViewModel = koinViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    wallpaper: CommonWallpaperEntity,
    onBack: () -> Unit
) {

    BackHandler(onBack = onBack)

    val uiState by detailVm.state.collectAsStateWithLifecycle()

    val mContext = LocalContext.current

    Box(contentAlignment = Alignment.BottomCenter, modifier = modifier.fillMaxSize()) {

        AsyncImage(
            model = ImageRequest.Builder(mContext)
                .data(wallpaper.url)
                .listener(onSuccess = { _, res ->
                    detailVm.onEvent(DetailScreenEvents.UpdateWallpaper(res.drawable))
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
            isFavourite = uiState.isFavourite,
            onApply = {
                detailVm.onEvent(DetailScreenEvents.ToggleDialog(true))
            }, onFavourite = {
                actionViewModel.onEvent(UiEvents.AddOrRemove(wallpaper))
                detailVm.onEvent(DetailScreenEvents.ToggleFavourite)
            }, onDownload = {
                downloadWallpaper(actionViewModel, wallpaperUrl = wallpaper.url, mContext)
            })
    }


    /** Dialog display on apply button press*/
    if (uiState.showDialog) {
        if (uiState.wallpaperDrawable != null) {
            WallpaperApplyDialog(
                wallpaper = uiState.wallpaperDrawable,
                onDismissRequest = {
                    detailVm.onEvent(DetailScreenEvents.ToggleDialog(false))
                })
        }
    }
}

/** download wallpaper from server */
private fun downloadWallpaper(
    actionViewModel: ActionViewModel, wallpaperUrl: String, context: Context
) {
    actionViewModel.onEvent(UiEvents.DownloadWallpaper(wallpaperUrl))
    Toast.makeText(context, context.getString(R.string.downloading), Toast.LENGTH_SHORT).show()
}