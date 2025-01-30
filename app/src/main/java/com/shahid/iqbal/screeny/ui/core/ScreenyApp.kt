package com.shahid.iqbal.screeny.ui.core

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
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
import com.shahid.iqbal.screeny.ui.routs.Routs.Setting
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
import com.shahid.iqbal.screeny.ui.screens.settings.core.SettingScreen
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


    Scaffold(bottomBar = { if (canShowBottomBar) BottomNavigationBar(navController) }, topBar = {
        if (canShowTopBar) {


            val title = titleMapper(stackEntry?.destination?.route?.substringAfterLast("."))
            TopBar(title = title) { navController.navigate(Routs.SearchedWallpaper) }
        }
    }, modifier = Modifier.fillMaxSize(),

        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->

        SharedTransitionLayout() {

            NavHost(
                navController = navController, startDestination = Splash,
                enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)


            ) {


                composable<Splash> {
                    SplashScreen {
                        navController.navigate(
                            Home, navOptions = NavOptions.Builder().setPopUpTo(Splash, true).build()
                        )
                    }
                }

                composable<Home> {
                    HomeScreen(wallpapers, onWallpaperClick = { index ->
                        wallpaperCLick(
                            index, wallpapers.itemSnapshotList.items, sharedWallpaperViewModel, navController
                        )
                    }, onBack = { exitProcess(0) })
                }

                composable<Categories> {
                    CategoryScreen { category ->
                        navController.navigate(Routs.CategoryDetail(category))
                    }
                }

                composable<Favourite> { FavouriteScreen(navController = navController, animatedVisibilityScope = this@composable) }

                composable<Setting> { SettingScreen(navController) }


                composable<Routs.CategoryDetail> { backStackEntry ->
                    val categoryDetail: Routs.CategoryDetail = backStackEntry.toRoute()
                    category = categoryDetail.query

                    CategoryDetailScreen(category, categoriesWiseWallpaperList, onBackClick = { navController.navigateUp() }, onWallpaperClick = { index ->
                        wallpaperCLick(
                            index, categoriesWiseWallpaperList.itemSnapshotList.items, sharedWallpaperViewModel, navController
                        )
                    })
                }

                composable<Routs.SearchedWallpaper> {
                    SearchedWallpaperScreen(onNavigateBack = { navController.navigateUp() }, onWallpaperClick = { index, list ->
                        wallpaperCLick(index, list, sharedWallpaperViewModel, navController)
                    })
                }

                composable<Routs.WallpaperDetail> {
                    WallpaperDetailScreen(sharedWallpaperViewModel) {
                        navController.navigateUp()
                    }
                }

                composable<Routs.FavouriteDetail> { backStackEntry ->
                    val wallpaperId = backStackEntry.toRoute<Routs.FavouriteDetail>().wallpaperId
                    val wallpaperUrl = backStackEntry.toRoute<Routs.FavouriteDetail>().wallpaperUrl
                    FavouriteDetailScreen(
                        animatedVisibilityScope = this@composable, wallpaper = CommonWallpaperEntity(wallpaperId, wallpaperUrl), navController = navController
                    )
                }


                composable<Routs.Language> {
                    LanguageScreen(navController = navController)
                }

            }

        }
    }


}

private fun wallpaperCLick(
    index: Int,
    list: List<Wallpaper>,
    sharedWallpaperViewModel: SharedWallpaperViewModel,
    navController: NavHostController,
) {
    sharedWallpaperViewModel.updateWallpaperList(list)
    sharedWallpaperViewModel.updateSelectedWallpaper(wallpaper = list[index])
    navController.navigate(Routs.WallpaperDetail)
}





