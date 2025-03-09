package com.shahid.iqbal.screeny.ui.screens.wallpapers

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.data.utils.toCommonWallpaperEntity
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.ui.screens.components.ActionButtons
import com.shahid.iqbal.screeny.ui.screens.components.BlurBg
import com.shahid.iqbal.screeny.ui.screens.components.SinglePageContent
import com.shahid.iqbal.screeny.ui.theme.ActionIconBgColor
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun WallpaperDetailScreen(
    wallpapers: List<Wallpaper>,
    index: Int,
    imageLoader: ImageLoader,
    actionViewModel: ActionViewModel = koinViewModel(),
    onBack: () -> Unit
) {

    val favouriteList by actionViewModel.getAllFavourites.collectAsStateWithLifecycle()
    var canShowDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = if (index != -1) index else 0) { wallpapers.size }


    var canShowList by remember { mutableStateOf(false) }
    var isFavourite by remember { mutableStateOf(false) }

    val currentlyLoadedWallpaper = remember { mutableStateOf<Drawable?>(null) }


    LaunchedEffect(key1 = canShowList) {
        delay(100)
        canShowList = true
    }

    LaunchedEffect(key1 = pagerState.currentPage, favouriteList) {
        isFavourite = favouriteList.fastAny { it.wallpaper == wallpapers[pagerState.currentPage].wallpaperSource.portrait }
    }




    if (!canShowList) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round
            )
        }
    }

    AnimatedVisibility(
        visible = canShowList, modifier = Modifier.fillMaxSize()
    ) {

        Box(
            contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()

        ) {

            BlurBg(wallpapers[pagerState.currentPage].wallpaperSource.portrait, onLoaded = { currentlyLoadedWallpaper.value = it })

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier
                    .padding(start = 10.dp, top = 50.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(color = ActionIconBgColor, shape = CircleShape)
                    .padding(5.dp)
                    .align(Alignment.TopStart)
                    .noRippleClickable { onBack() }
                    .zIndex(90f), tint = Color.White)


            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp),
                beyondViewportPageCount = 0,
                key = { wallpapers[it].id },
            ) { page ->

                val currentWallpaper = wallpapers[page].wallpaperSource.portrait
                SinglePageContent(
                    wallpaperUrl = currentWallpaper,
                    imageLoader = imageLoader,
                    pagerState = pagerState,
                    page = page
                )

            }


            ActionButtons(
                isFavourite = isFavourite,
                onDownload = {
                    downloadWallpaper(actionViewModel, pagerState, wallpapers, context)
                }, onApply = {
                    canShowDialog = true
                }, onFavourite = {
                    val wallpaper = wallpapers[pagerState.currentPage]
                    actionViewModel.addOrRemove(wallpaper = wallpaper.toCommonWallpaperEntity())
                })
        }

        if (canShowDialog)
            WallpaperApplyDialog(
                wallpaper = currentlyLoadedWallpaper.value,
                onDismissRequest = { canShowDialog = false })

    }

}


private fun downloadWallpaper(
    actionViewModel: ActionViewModel, pagerState: PagerState, wallpapers: List<Wallpaper>, context: Context
) {
    actionViewModel.downloadWallpaper(
        url = wallpapers[pagerState.currentPage].wallpaperSource.portrait
    )
    Toast.makeText(context, context.getString(R.string.downloading), Toast.LENGTH_SHORT).show()
}


