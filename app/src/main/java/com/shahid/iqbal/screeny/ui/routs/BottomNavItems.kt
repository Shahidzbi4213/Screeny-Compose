package com.shahid.iqbal.screeny.ui.routs

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import com.shahid.iqbal.screeny.R

sealed interface IconType {

    @Stable
    @Keep
    data class Vector(val icon: ImageVector) : IconType

    @Stable
    @Keep
    data class Drawable(@DrawableRes val iconRes: Int) : IconType
}


@Stable
@Keep
data class BottomNavRoutes(@StringRes val name: Int, val route: Routs, val icon: IconType, val selectedIcon: IconType)

val bottomNavigationItems = listOf(
    BottomNavRoutes(
        name = R.string.home, route = Routs.Home, icon = IconType.Vector(Icons.Outlined.Home), selectedIcon = IconType.Vector(Icons.Filled.Home)
    ), BottomNavRoutes(
        name = R.string.category, route = Routs.Categories, icon = IconType.Drawable(R.drawable.category), selectedIcon = IconType.Drawable(R.drawable.category_filled)
    ), BottomNavRoutes(
        name = R.string.favourite, route = Routs.Favourite, icon = IconType.Vector(Icons.Outlined.FavoriteBorder), selectedIcon = IconType.Vector(Icons.Filled.Favorite)
    ), BottomNavRoutes(
        name = R.string.settings, route = Routs.Settings, icon = IconType.Vector(Icons.Outlined.Settings), selectedIcon = IconType.Vector(Icons.Filled.Settings)
    )
)

