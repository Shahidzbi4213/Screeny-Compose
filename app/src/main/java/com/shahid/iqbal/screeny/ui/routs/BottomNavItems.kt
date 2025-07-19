package com.shahid.iqbal.screeny.ui.routs

import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import com.shahid.iqbal.screeny.R

@Stable
@Keep
data class BottomNavRoutes(@StringRes val name: Int, val route: Routs, val icon: ImageVector, val selectedIcon: ImageVector)

val bottomNavigationItems = listOf(
    BottomNavRoutes(
        name = R.string.home, route = Routs.Home, icon = Icons.Outlined.Home, selectedIcon = Icons.Filled.Home
    ), BottomNavRoutes(
        name = R.string.category, route = Routs.Categories, icon = Icons.Outlined.Category, selectedIcon = Icons.Filled.Category
    ), BottomNavRoutes(
        name = R.string.favourite, route = Routs.Favourite, icon = Icons.Outlined.FavoriteBorder, selectedIcon = Icons.Filled.Favorite
    ), BottomNavRoutes(
        name = R.string.settings, route = Routs.Settings, icon = Icons.Outlined.Settings, selectedIcon = Icons.Filled.Settings
    )
)

