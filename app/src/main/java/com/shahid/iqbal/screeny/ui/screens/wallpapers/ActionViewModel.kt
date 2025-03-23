package com.shahid.iqbal.screeny.ui.screens.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahid.iqbal.screeny.data.repositories.FavouriteRepo
import com.shahid.iqbal.screeny.models.CommonWallpaperEntity
import com.shahid.iqbal.screeny.utils.WallpaperDownloader
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed interface UiEvents {

    data class AddOrRemove(val wallpaper: CommonWallpaperEntity) : UiEvents
    data class DownloadWallpaper(val url: String) : UiEvents
}

class ActionViewModel(
    private val favouriteRepo: FavouriteRepo,
    private val downloader: WallpaperDownloader
) : ViewModel() {


    val getAllFavourites = favouriteRepo.getAllFavourites
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun onEvent(event: UiEvents) {
        when (event) {
            is UiEvents.AddOrRemove -> {
                addOrRemove(event.wallpaper)
            }

            is UiEvents.DownloadWallpaper ->
                downloadWallpaper(event.url)
        }
    }

    private fun addOrRemove(wallpaper: CommonWallpaperEntity) {
        viewModelScope.launch { favouriteRepo.addOrRemove(wallpaper) }
    }


    private fun downloadWallpaper(url: String) {
        downloader.downloadFile(url)
    }
}