package com.shahid.iqbal.screeny.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahid.iqbal.screeny.data.repositories.FavouriteRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavouriteViewModel(private val favouriteRepo: FavouriteRepo) : ViewModel() {

    val getAllFavourites = favouriteRepo.getAllFavourites
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())



    fun removeFromFavourite(wallpaperUrl: String) {
        viewModelScope.launch {
            favouriteRepo.removeWallpaper(wallpaperUrl)
        }
    }
}