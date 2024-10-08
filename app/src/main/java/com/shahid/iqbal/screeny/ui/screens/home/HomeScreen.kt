package com.shahid.iqbal.screeny.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.ui.screens.components.LoadingPlaceHolder
import com.shahid.iqbal.screeny.ui.screens.components.WallpaperItem
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun HomeScreen(
    wallpaperViewModel: WallpaperViewModel,
    modifier: Modifier = Modifier,
    onWallpaperClick: (Int, List<Wallpaper>) -> Unit,
    onBack: () -> Unit
) {

    val wallpapers = wallpaperViewModel.getAllWallpapers.collectAsLazyPagingItems()
    val imageLoader: ImageLoader = koinInject()

    BackHandler { onBack() }



    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
    ) {


        items(wallpapers.itemCount, key = { "${wallpapers[it]?.id}_$it" }) { index ->

            val wallpaper = wallpapers[index]
            if (wallpaper != null) {
                WallpaperItem(wallpaper = wallpaper.wallpaperSource.portrait, imageLoader) {
                    onWallpaperClick(index, wallpapers.itemSnapshotList.items)
                }
            }

        }
    }


}


