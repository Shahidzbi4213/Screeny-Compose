package com.shahid.iqbal.screeny.ui.screens.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.shahid.iqbal.screeny.ui.routs.Routs.CategoryDetail
import com.shahid.iqbal.screeny.ui.routs.Routs.FavouriteDetail
import com.shahid.iqbal.screeny.ui.routs.Routs.Language
import com.shahid.iqbal.screeny.ui.routs.Routs.SearchedWallpaper
import com.shahid.iqbal.screeny.ui.routs.Routs.Splash
import com.shahid.iqbal.screeny.ui.routs.Routs.WallpaperDetail

@Composable
fun ManageBarVisibility(
    currentEntry: () -> NavBackStackEntry?,
    showTopBar: (Boolean) -> Unit,
    showBottomBar: (Boolean) -> Unit,
) {
    currentEntry()?.let { entry ->

        val route = entry.destination.route?.substringBefore("/")
        when (route) {

            in arrayOf(
                Splash::class.qualifiedName,
                CategoryDetail::class.qualifiedName,
                SearchedWallpaper::class.qualifiedName,
                WallpaperDetail::class.qualifiedName,
                FavouriteDetail::class.qualifiedName,
                Language::class.qualifiedName

            ) -> {
                showTopBar(false)
                showBottomBar(false)
            }

            else -> {
                showTopBar(true)
                showBottomBar(true)
            }
        }

    }
}

