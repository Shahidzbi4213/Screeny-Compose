package com.shahid.iqbal.screeny.ui.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shahid.iqbal.screeny.R
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
import com.shahid.iqbal.screeny.ui.screens.favourite.FavouriteScreen
import com.shahid.iqbal.screeny.ui.screens.home.HomeScreen
import com.shahid.iqbal.screeny.ui.screens.home.WallpaperViewModel
import com.shahid.iqbal.screeny.ui.screens.search.SearchedWallpaperScreen
import com.shahid.iqbal.screeny.ui.screens.settings.SettingScreen
import com.shahid.iqbal.screeny.ui.screens.splash.SplashScreen
import com.shahid.iqbal.screeny.ui.screens.wallpapers.WallpaperDetailScreen
import com.shahid.iqbal.screeny.ui.shared.SharedWallpaperViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.system.exitProcess


@Composable
fun ScreenyApp() {


    val navController = rememberNavController()
    var canShowBottomBar by rememberSaveable { mutableStateOf(false) }
    var canShowTopBar by rememberSaveable { mutableStateOf(false) }
    val stackEntry by navController.currentBackStackEntryAsState()

    val wallpaperViewModel: WallpaperViewModel = koinViewModel()

    val sharedWallpaperViewModel = koinViewModel<SharedWallpaperViewModel>()
    val context = LocalContext.current



    ManageBarVisibility(
        context = context,
        currentEntry = { stackEntry },
        showTopBar = { canShowTopBar = it },
        showBottomBar = { canShowBottomBar = it },
    )

    Scaffold(bottomBar = {
        if (canShowBottomBar) BottomNavigationBar(navController)
    }, topBar = {
        if (canShowTopBar) {

            val title = stackEntry?.destination?.route?.substringAfterLast(".") ?: stringResource(id = R.string.app_name)

            TopBar(title = title) {
                navController.navigate(Routs.SearchedWallpaper)
            }
        }
    }) { innerPadding ->

        NavHost(
            navController, startDestination = Splash,
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            composable<Splash> {
                SplashScreen {
                    navController.navigate(Home, navOptions = NavOptions.Builder().setPopUpTo(Splash, true).build())
                }
            }

            composable<Home> {
                HomeScreen(
                    wallpaperViewModel,
                    onWallpaperClick = { index, list -> wallpaperCLick(index, list, sharedWallpaperViewModel, navController) },
                    onBack = { exitProcess(0) }
                )
            }

            composable<Categories> {
                CategoryScreen { category ->
                    navController.navigate(Routs.CategoryDetail(category))
                }
            }

            composable<Favourite> {
                FavouriteScreen()
            }

            composable<Setting> {
                SettingScreen()
            }


            composable<Routs.CategoryDetail> { backStackEntry ->
                val categoryDetail: Routs.CategoryDetail = backStackEntry.toRoute()
                val categoryViewModel = koinViewModel<CategoryViewModel>()
                CategoryDetailScreen(categoryDetail.query, categoryViewModel,
                    onBackClick = { navController.navigateUp() },
                    onWallpaperClick = { index, list ->
                        wallpaperCLick(index, list, sharedWallpaperViewModel, navController)
                    })
            }

            composable<Routs.SearchedWallpaper> {
                SearchedWallpaperScreen(onNavigateBack = { navController.navigateUp() },
                    onWallpaperClick = { index, list ->
                        wallpaperCLick(index, list, sharedWallpaperViewModel, navController)
                    })
            }

            composable<Routs.WallpaperDetail> {
                WallpaperDetailScreen(sharedWallpaperViewModel)
            }

        }

    }
}

private fun wallpaperCLick(
    index: Int,
    list: List<Wallpaper>,
    sharedWallpaperViewModel: SharedWallpaperViewModel,
    navController: NavHostController
) {
    sharedWallpaperViewModel.updateWallpaperList(list)
    sharedWallpaperViewModel.updateSelectedWallpaper(list[index])
    navController.navigate(Routs.WallpaperDetail)
}





