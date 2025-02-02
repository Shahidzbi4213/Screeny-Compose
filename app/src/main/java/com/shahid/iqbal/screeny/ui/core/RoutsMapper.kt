package com.shahid.iqbal.screeny.ui.core;

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.shahid.iqbal.screeny.R

/**
 * Maps a route name to a localized title string.
 *
 * This function takes a route name as input and returns the corresponding title string
 * that should be displayed in the UI (e.g., in an app bar). It uses string resources
 * for localization. If the route name is not recognized, it defaults to the application's name.
 *
 * @param routName The name of the route. This is typically a string identifier
 *                 representing a screen or section in the application (e.g., "Home", "Categories").
 *                 Can be null, in which case it will default to the app name.
 * @return A localized string resource representing the title for the given route name.
 *         If the route name is not recognized, returns the application's name.
 *
 * @sample
 * ```kotlin
 * Text(text = titleMapper("Home")) // Displays the localized string for "Home"
 * Text(text = titleMapper("Categories")) // Displays the localized string for "Categories"
 * Text(text = titleMapper("UnknownRoute")) // Displays the application name
 * Text(text = titleMapper(null)) //Displays the application name
 * ```
 */

@ReadOnlyComposable
@Composable
fun titleMapper(routName: String?): String {
    return when (routName) {
        "Home" -> stringResource(id = R.string.home)
        "Categories" -> stringResource(id = R.string.categories)
        "Favourite" -> stringResource(id = R.string.favourite)
        "Settings" -> stringResource(id = R.string.settings)
        else -> stringResource(id = R.string.app_name)
    }
}