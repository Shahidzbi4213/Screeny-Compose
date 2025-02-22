package com.shahid.iqbal.screeny.ui.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.RecentSearch
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.ui.screens.components.Footer
import com.shahid.iqbal.screeny.ui.screens.components.LoadingPlaceHolder
import com.shahid.iqbal.screeny.ui.screens.components.WallpaperItem
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedWallpaperScreen(
    onNavigateBack: () -> Unit,
    onWallpaperClick: (Wallpaper, List<Wallpaper>) -> Unit,
) {

    val imageLoader = koinInject<ImageLoader>()
    val searchViewModel = koinViewModel<SearchViewModel>()

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    val recentSearches by searchViewModel.recentSearches.collectAsStateWithLifecycle()
    val searchedWallpapers = searchViewModel.searchWallpapers(searchQuery).collectAsLazyPagingItems()

    val localKeyboard = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()

    ) {

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = if (!isExpanded) 20.dp else 0.dp),
            windowInsets = if (isExpanded) {
                WindowInsets(0.dp)
            } else {
                WindowInsets(top = 20.dp)
            },
            inputField = {
                InputField(query = searchQuery, onQueryChange = { searchQuery = it }, onSearch = {
                    if (searchQuery.isNotEmpty() && searchQuery.length >= 3) {
                        isExpanded = false
                        searchViewModel.saveRecentSearch(searchQuery)
                        localKeyboard?.hide()
                        localFocusManager.clearFocus(true)
                    }
                }, expanded = false, onExpandedChange = { isExpanded = it }, placeholder = {
                    Text(
                        stringResource(id = R.string.search_wallpaper),
                    )
                }, leadingIcon = {
                    if (searchQuery.isEmpty() && !isExpanded) Icon(
                        Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface
                    ) else Icon(
                        Icons.AutoMirrored.Default.ArrowBack, contentDescription = null, modifier = Modifier.clickable {
                            searchQuery = ""
                            isExpanded = false
                            onNavigateBack()
                        }, tint = MaterialTheme.colorScheme.onSurface
                    )
                }, trailingIcon = {
                    if (searchQuery.isNotEmpty()) Icon(
                        Icons.Default.Clear, contentDescription = null,
                        modifier = Modifier.clickable {
                            searchQuery = ""
                            isExpanded = true
                        }, tint = if (isExpanded) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                },


                    modifier = if (isExpanded) {
                        Modifier
                            .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                            .focusRequester(focusRequester)
                    } else {
                        Modifier
                            .fillMaxWidth()
                            .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                            .focusRequester(focusRequester)
                    }, colors = TextFieldDefaults.colors(
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            colors = SearchBarDefaults.colors(
                containerColor = if (isExpanded) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                }, dividerColor = MaterialTheme.colorScheme.outline
            ),
            tonalElevation = 0.dp,
        ) {
            RecentSearches(searchViewModel, recentSearches,
                onRecentItemClick = {
                    searchQuery = it
                    localKeyboard?.show()
                    focusRequester.requestFocus()
                })
        }

        Spacer(modifier = Modifier.height(20.dp))
        ShowWallpapers(searchedWallpapers, imageLoader, onWallpaperClick)
    }
}


@Composable
private fun RecentSearches(
    searchViewModel: SearchViewModel, recentSearches: List<RecentSearch>, onRecentItemClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {

        if (recentSearches.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.recent_searchs),
                    fontFamily = screenyFontFamily,
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier.weight(1f)
                )

                TextButton(onClick = { searchViewModel.clearAllRecent() }) {
                    Text(text = "Clear All")
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            items(recentSearches) {
                SingleRecentItem(recentSearch = it) { onRecentItemClick(it.query) }
            }
        }
    }
}

@Composable
fun ShowWallpapers(
    wallpapers: LazyPagingItems<Wallpaper>, imageLoader: ImageLoader, onWallpaperClick: (Wallpaper: Wallpaper, items: List<Wallpaper>) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {

        if (wallpapers.loadState.refresh == LoadState.Loading) {
            items(20) {
                LoadingPlaceHolder(modifier = Modifier.height(200.dp))
            }
        }

        items(wallpapers.itemCount, key = { "${wallpapers[it]?.id}_$it" }) { index ->
            if (index < wallpapers.itemCount) {
                val wallpaper = wallpapers[index]
                if (wallpaper != null) {
                    WallpaperItem(wallpaper = wallpaper.wallpaperSource.portrait, imageLoader = imageLoader) {
                        onWallpaperClick(
                            wallpaper,
                            wallpapers.itemSnapshotList.items
                        )
                    }
                }
            }
        }

        if (wallpapers.loadState.append == LoadState.Loading)
            item(span = { GridItemSpan(this.maxLineSpan) }) {
                Footer()
            }
    }
}


@Composable
fun SingleRecentItem(recentSearch: RecentSearch, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.history),
            contentDescription = null, tint = MaterialTheme.colorScheme.primary
        )
        Text(text = recentSearch.query, fontFamily = screenyFontFamily,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant), modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .noRippleClickable { onClick() })
    }
}