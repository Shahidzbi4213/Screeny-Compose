package com.shahid.iqbal.screeny.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahid.iqbal.screeny.models.Wallpaper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedWallpaperViewModel() : ViewModel() {

    private val _wallpaperList = MutableStateFlow<List<Wallpaper>>(emptyList())
    val wallpaperList get() = _wallpaperList.asStateFlow()


    private val _selectedWallpaperIndex = MutableStateFlow(0)
    val selectedWallpaperIndex get() = _selectedWallpaperIndex.asStateFlow()


    fun updateWallpaperDetails(wallpaper: Wallpaper, wallpapers: List<Wallpaper>) {
        viewModelScope.launch {
            _wallpaperList.update { wallpapers }
            _selectedWallpaperIndex.update { wallpapers.indexOf(wallpaper) }

        }
    }


}