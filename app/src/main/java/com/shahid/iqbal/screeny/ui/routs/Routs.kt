package com.shahid.iqbal.screeny.ui.routs

import android.graphics.drawable.Drawable
import androidx.annotation.Keep
import androidx.compose.runtime.Stable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Keep
@Serializable
sealed interface Routs {

    @Serializable
    data object Splash : Routs

    @Serializable
    data object Home : Routs

    @Serializable
    data object Categories : Routs

    @Serializable
    data object Favourite : Routs

    @Serializable
    data class FavouriteDetail(val wallpaperId: Long , val wallpaperUrl: String) : Routs

    @Serializable
    data object Settings : Routs

    @Serializable
    @Stable
    data class CategoryDetail(val query: String) : Routs

    @Serializable
    data object SearchedWallpaper : Routs

    @Serializable
    data object WallpaperDetail : Routs


    @Serializable
    data object Language : Routs


}