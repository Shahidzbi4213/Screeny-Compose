package com.google.screeny.ui.composables

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import screeny.composeapp.generated.resources.Res
import screeny.composeapp.generated.resources.app_name
import screeny.composeapp.generated.resources.categories
import screeny.composeapp.generated.resources.favourite
import screeny.composeapp.generated.resources.home
import screeny.composeapp.generated.resources.settings

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

@Composable
fun titleMapper(routName: String?): String {
    return when (routName) {
        "Home" -> stringResource( Res.string.home)
        "Categories" -> stringResource(Res.string.categories)
        "Favourite" -> stringResource(Res.string.favourite)
        "Settings" -> stringResource(Res.string.settings)
        else -> stringResource(Res.string.app_name)
    }
}