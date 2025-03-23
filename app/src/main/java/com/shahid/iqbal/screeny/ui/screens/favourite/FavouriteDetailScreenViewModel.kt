package com.shahid.iqbal.screeny.ui.screens.favourite

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


data class FavouriteDetailScreenState(
    var showDialog: Boolean = false,
    var isFavourite: Boolean = true,
    var wallpaperDrawable: Drawable? = null
)

sealed interface DetailScreenEvents {

    data object ToggleFavourite : DetailScreenEvents
    data class ToggleDialog(val value: Boolean) : DetailScreenEvents
    data class UpdateWallpaper(val drawable: Drawable) : DetailScreenEvents
}

class FavouriteDetailScreenViewModel : ViewModel() {

    private var _state = MutableStateFlow(FavouriteDetailScreenState())
    var state: StateFlow<FavouriteDetailScreenState> = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavouriteDetailScreenState())


    fun onEvent(event: DetailScreenEvents) {
        when (event) {
            is DetailScreenEvents.UpdateWallpaper -> {
                _state.update { it.copy(wallpaperDrawable = event.drawable) }
            }

            DetailScreenEvents.ToggleFavourite -> {
                _state.update { it.copy(isFavourite = !it.isFavourite) }
            }

            is DetailScreenEvents.ToggleDialog -> {
                _state.update { it.copy(showDialog = event.value) }
            }
        }
    }

}