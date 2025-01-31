package com.shahid.iqbal.screeny.ui.screens.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shahid.iqbal.screeny.ui.routs.IconType
import com.shahid.iqbal.screeny.ui.routs.bottomNavigationItems
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MaterialTheme.colorScheme

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

    ) {
        bottomNavigationItems.forEach { bottomNavItem ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route::class.qualifiedName } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomNavItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (bottomNavItem.icon) {
                        is IconType.Drawable -> {
                            Icon(
                                painter = painterResource(
                                    id = if (isSelected && bottomNavItem.selectedIcon is IconType.Drawable)
                                        bottomNavItem.selectedIcon.iconRes
                                    else bottomNavItem.icon.iconRes
                                ),
                                contentDescription = stringResource(id = bottomNavItem.name),
                            )
                        }
                        is IconType.Vector -> {
                            Icon(
                                imageVector = if (isSelected && bottomNavItem.selectedIcon is IconType.Vector)
                                    bottomNavItem.selectedIcon.icon
                                else bottomNavItem.icon.icon,
                                contentDescription = stringResource(id = bottomNavItem.name),
                            )
                        }
                    }
                },
                alwaysShowLabel = false,
            )
        }
    }
}
