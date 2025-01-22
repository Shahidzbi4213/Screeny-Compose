package com.shahid.iqbal.screeny.ui.screens.favourite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.ImageLoader
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.routs.Routs
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavouriteScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavController
) {

    val favouriteViewModel = koinViewModel<FavouriteViewModel>()
    val favourites by favouriteViewModel.getAllFavourites.collectAsStateWithLifecycle(emptyList())
    val imageLoader = koinInject<ImageLoader>()


    if (favourites.isEmpty()) {
        NoFavouritePlaceholder(onExplore = { navController.navigate(Routs.Home) })
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .safeContentPadding()
                .padding(top = 45.dp)
                .fillMaxSize()

        ) {

            items(favourites, key = { favourite -> favourite.timeStamp }) { favourite ->
                FavouriteWallpaperItem(
                    wallpaper = favourite.wallpaper, imageLoader = imageLoader,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onWallpaperClick = { wallpaper -> navController.navigate(Routs.FavouriteDetail(favourite.id, wallpaper)) },
                    onRemoveFromFavClick = { wallpaper -> favouriteViewModel.removeFromFavourite(wallpaper) }
                )
            }

        }
    }


}

@Composable
fun NoFavouritePlaceholder(onExplore: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_favourite_found),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = stringResource(R.string.your_favorite_wallpapers_will_appear_here_start_exploring_and_add_some_to_your_favorites),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 20.dp)
        )

        Button(
            onClick = onExplore,
            modifier = Modifier.padding(top = 16.dp),
            interactionSource = NoRippleInteractionSource()
        ) {
            Text(text = stringResource(R.string.explore_wallpapers))
        }
    }
}


