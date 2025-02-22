package com.shahid.iqbal.screeny.ui.core

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahid.iqbal.screeny.models.CommonWallpaperEntity
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.ui.routs.Routs
import com.shahid.iqbal.screeny.ui.routs.Routs.Categories
import com.shahid.iqbal.screeny.ui.routs.Routs.Favourite
import com.shahid.iqbal.screeny.ui.routs.Routs.Home
import com.shahid.iqbal.screeny.ui.routs.Routs.Settings
import com.shahid.iqbal.screeny.ui.routs.Routs.Splash
import com.shahid.iqbal.screeny.ui.screens.category.CategoryDetailScreen
import com.shahid.iqbal.screeny.ui.screens.category.CategoryScreen
import com.shahid.iqbal.screeny.ui.screens.category.CategoryViewModel
import com.shahid.iqbal.screeny.ui.screens.components.BottomNavigationBar
import com.shahid.iqbal.screeny.ui.screens.components.ManageBarVisibility
import com.shahid.iqbal.screeny.ui.screens.components.TopBar
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteDetailScreen
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteScreen
import com.shahid.iqbal.screeny.ui.screens.home.HomeScreen
import com.shahid.iqbal.screeny.ui.screens.home.WallpaperViewModel
import com.shahid.iqbal.screeny.ui.screens.search.SearchedWallpaperScreen
import com.shahid.iqbal.screeny.ui.screens.settings.core.SettingsScreen
import com.shahid.iqbal.screeny.ui.screens.settings.language.LanguageScreen
import com.shahid.iqbal.screeny.ui.screens.splash.SplashScreen
import com.shahid.iqbal.screeny.ui.screens.wallpapers.WallpaperDetailScreen
import com.shahid.iqbal.screeny.ui.shared.SharedWallpaperViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.system.exitProcess

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ScreenyApp() {


    val navController = rememberNavController()
    var canShowBottomBar by rememberSaveable { mutableStateOf(false) }
    var canShowTopBar by rememberSaveable { mutableStateOf(false) }
    val stackEntry by navController.currentBackStackEntryAsState()


    val wallpaperViewModel: WallpaperViewModel = koinViewModel()
    val wallpapers = wallpaperViewModel.getAllWallpapers.collectAsLazyPagingItems()

    val categoryViewModel: CategoryViewModel = koinViewModel()
    var category by rememberSaveable { mutableStateOf("") }
    val categoriesWiseWallpaperList = categoryViewModel.searchWallpapers(category).collectAsLazyPagingItems()

    val sharedWallpaperViewModel: SharedWallpaperViewModel = koinViewModel()

    ManageBarVisibility(
        currentEntry = { stackEntry },
        showTopBar = { canShowTopBar = it },
        showBottomBar = { canShowBottomBar = it },
    )


    Scaffold(
        bottomBar = { if (canShowBottomBar) BottomNavigationBar(navController) },
        topBar = {
            if (canShowTopBar) {


                val title = titleMapper(stackEntry?.destination?.route?.substringAfterLast("."))
                TopBar(title = title) { navController.navigate(Routs.SearchedWallpaper) }
            }
        },
        modifier = Modifier
            .fillMaxSize(),

        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->

        SharedTransitionLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            NavHost(
                navController = navController, startDestination = Splash,
                modifier = Modifier
            ) {


                composable<Splash> {
                    SplashScreen {
                        navController.navigate(
                            Home, navOptions =
                            NavOptions.Builder()
                                .setPopUpTo(Splash, true).build()
                        )
                    }
                }

                composable<Home> {
                    HomeScreen(wallpapers, onWallpaperClick = { wallpaper ->
                        wallpaperCLick(
                            wallpaper,
                            wallpapers.itemSnapshotList.items,
                            sharedWallpaperViewModel,
                            navController
                        )
                    }, onBack = { exitProcess(0) })
                }


                composable<Categories> {
                    CategoryScreen { category ->
                        navController.navigate(Routs.CategoryDetail(category))
                    }
                }

                composable<Favourite> {
                    FavouriteScreen(animatedVisibilityScope = this@composable,
                        onExplore = { navController.navigate(Routs.Home) },
                        onWallpaperClick = { id, wallpaper ->
                            navController.navigate(Routs.FavouriteDetail(id, wallpaper))
                        })
                }

                composable<Settings> { SettingsScreen(navController) }


                composable<Routs.CategoryDetail> { backStackEntry ->
                    val categoryDetail: Routs.CategoryDetail = backStackEntry.toRoute()
                    category = categoryDetail.query

                    CategoryDetailScreen(
                        category, categoriesWiseWallpaperList, onBackClick = navController::navigateUp,
                        onWallpaperClick = { wallpaper ->
                            wallpaperCLick(
                                wallpaper,
                                categoriesWiseWallpaperList.itemSnapshotList.items,
                                sharedWallpaperViewModel,
                                navController
                            )
                        })
                }

                composable<Routs.SearchedWallpaper> {
                    SearchedWallpaperScreen(
                        onNavigateBack = navController::navigateUp,
                        onWallpaperClick = { wallpaper, list ->
                            wallpaperCLick(wallpaper, list, sharedWallpaperViewModel, navController)
                        })
                }

                composable<Routs.WallpaperDetail> {
                    val categoriesWallpaper by sharedWallpaperViewModel.wallpaperList.collectAsStateWithLifecycle()
                    val index by sharedWallpaperViewModel.selectedWallpaperIndex.collectAsStateWithLifecycle()

                    WallpaperDetailScreen(
                        wallpapers = categoriesWallpaper,
                        index = index,
                        onBack = navController::navigateUp
                    )
                }

                composable<Routs.FavouriteDetail> { backStackEntry ->
                    val wallpaperId = backStackEntry.toRoute<Routs.FavouriteDetail>().wallpaperId
                    val wallpaperUrl = backStackEntry.toRoute<Routs.FavouriteDetail>().wallpaperUrl
                    FavouriteDetailScreen(
                        animatedVisibilityScope = this@composable,
                        wallpaper = CommonWallpaperEntity(wallpaperId, wallpaperUrl),
                        onBack = navController::navigateUp
                    )
                }


                composable<Routs.Language> {
                    LanguageScreen(goBack = navController::navigateUp)
                }

            }

        }
    }


}

private fun wallpaperCLick(
    wallpaper: Wallpaper,
    list: List<Wallpaper>,
    sharedWallpaperViewModel: SharedWallpaperViewModel,
    navController: NavHostController,
) {
    sharedWallpaperViewModel.updateWallpaperDetails(wallpaper, list)
    navController.navigate(Routs.WallpaperDetail)
}





