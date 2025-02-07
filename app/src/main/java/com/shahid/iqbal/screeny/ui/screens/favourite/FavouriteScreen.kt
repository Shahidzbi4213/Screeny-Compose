package com.shahid.iqbal.screeny.ui.screens.favourite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.routs.Routs
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource
import com.shahid.iqbal.screeny.utils.Extensions.Pulsating
import com.shahid.iqbal.screeny.utils.Extensions.mirror
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FavouriteScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onExplore: () -> Unit
) {

    val favouriteViewModel = koinViewModel<FavouriteViewModel>()
    val favourites by favouriteViewModel.getAllFavourites.collectAsStateWithLifecycle(emptyList())
    val imageLoader = koinInject<ImageLoader>()


    if (favourites.isEmpty()) {
        NoFavouritePlaceholder(onExplore = onExplore)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
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
        AsyncImage(
            model = R.drawable.no_favourite_found,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier
                .mirror()
                .size(80.dp),

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

        Pulsating {
            Button(
                onClick = onExplore,
                modifier = Modifier.padding(top = 16.dp),
                interactionSource = NoRippleInteractionSource()
            ) {
                Text(text = stringResource(R.string.explore_wallpapers))
            }
        }
    }
}


