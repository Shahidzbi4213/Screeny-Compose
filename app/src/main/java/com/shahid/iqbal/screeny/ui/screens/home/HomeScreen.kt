package com.shahid.iqbal.screeny.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.ui.screens.components.Footer
import com.shahid.iqbal.screeny.ui.screens.components.LoadingPlaceHolder
import com.shahid.iqbal.screeny.ui.screens.components.WallpaperItem
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    wallpapers: LazyPagingItems<Wallpaper>,
    modifier: Modifier = Modifier,
    onWallpaperClick: (Int) -> Unit,
    onBack: () -> Unit
) {

    val imageLoader: ImageLoader = koinInject()

    BackHandler { onBack() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize(),

        ) {

        if (wallpapers.loadState.refresh == LoadState.Loading) {
            items(20) {
                LoadingPlaceHolder(modifier = Modifier.height(200.dp))
            }
        }


        items(wallpapers.itemCount, key = { "${wallpapers[it]?.id}_$it" }) { index ->

            val wallpaper = wallpapers[index]
            if (wallpaper != null) {
                WallpaperItem(wallpaper = wallpaper.wallpaperSource.portrait, imageLoader = imageLoader) {
                    onWallpaperClick(index)
                }
            }
        }

        if (wallpapers.loadState.append == LoadState.Loading)
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                Footer()
            }


    }


}


