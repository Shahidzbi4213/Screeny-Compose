package com.google.screeny.ui.core

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.screeny.ui.components.BottomNavigationBar
import com.google.screeny.ui.components.TopBar
import com.google.screeny.ui.composables.ManageBarVisibility
import com.google.screeny.ui.composables.titleMapper
import com.google.screeny.ui.routs.Routs
import com.google.screeny.ui.screens.splash.SplashScreen
import com.google.screeny.ui.theme.ScreenyTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    var canShowBottomBar by rememberSaveable { mutableStateOf(false) }
    var canShowTopBar by rememberSaveable { mutableStateOf(false) }
    var category by rememberSaveable { mutableStateOf("") }
    val stackEntry by navController.currentBackStackEntryAsState()

    ManageBarVisibility(
        currentEntry = { stackEntry },
        showTopBar = { canShowTopBar = it },
        showBottomBar = { canShowBottomBar = it },
    )

    ScreenyTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = true) {
        Scaffold(
            bottomBar = { if (canShowBottomBar) BottomNavigationBar(navController) },
            topBar = {
                if (canShowTopBar) {
                    val title = titleMapper(stackEntry?.destination?.route?.substringAfterLast("."))
                    TopBar(title = title) { navController.navigate(Routs.SearchedWallpaper) }
                }
            }, modifier = modifier
                .fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp)
        ) { innerPadding ->

            SharedTransitionLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController, startDestination = Routs.Splash, modifier = Modifier.fillMaxSize()
                ) {

                    composable<Routs.Splash> {
                        SplashScreen {
                            navController.navigate(Routs.Home) {
                                popUpTo(Routs.Splash) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    composable<Routs.Home>() {
                        Box() {
                            Text("i am home")
                        }
                    }

                }
            }
        }
    }
}