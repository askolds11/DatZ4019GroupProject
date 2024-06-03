package com.grupacetri.oopprojekts.core.ui.navigation.compose

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.SettingsNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.rememberCanNavigate

// Add icons to top level (NavigationRoute) destinations
private sealed class TopScreen(
    val route: NavigationRoute,
    val iconInactive: ImageVector,
    val iconActive: ImageVector,
    @StringRes val resourceId: Int
) {
    data object Event : TopScreen(
        EventNavigationRoute.EventList,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.event
    )

    data object History : TopScreen(
        HistoryNavigationRoute.HistoryList,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.history
    )
    data object Settings : TopScreen(
        SettingsNavigationRoute.SettingsList,
        Icons.Outlined.Settings,
        Icons.Filled.Settings,
        R.string.settings
    )
}

@Composable
fun BottomBar(
    navController: NavController,
) {
    val items = remember {
        listOf(
            TopScreen.Event,
            TopScreen.History,
            TopScreen.Settings
        )
    }
    NavigationBar(
        tonalElevation = 0.dp,
    ) {
        // see https://developer.android.com/jetpack/compose/navigation#bottom-nav
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val canNavigate by rememberCanNavigate()
        var selectedIndex by remember { mutableIntStateOf(0) }
        LaunchedEffect(currentDestination) {
            // bug in navigation compose beta - hasRoute doesn't work for subgraphs.
            // workaround - use index, which doesn't work if using back to switch between
            // bottom bar items, but if possible use the hasRoute
            val nowSelectedIndex = items.indexOfFirst { screen ->
                currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true
            }
            if (nowSelectedIndex != -1) {
                selectedIndex = nowSelectedIndex
            }
        }
        items.forEachIndexed { index, screen ->
//            val selected = currentDestination?.hierarchy?.any {
//                Log.d("NavTest", "HasRoute: ${it.hasRoute(screen.route::class)} - $it")
//                it.hasRoute(screen.route::class)
//            } == true
            val selected by remember(selectedIndex) {
                derivedStateOf {
                    selectedIndex == index
                }
            }
            NavigationBarItem(
                icon = {
                    if (selected) {
                        Icon(screen.iconActive, contentDescription = null)
                    } else {
                        Icon(screen.iconInactive, contentDescription = null)
                    }
                },
                label = {
                    Text(text = stringResource(screen.resourceId))
                },
                selected = selected,
                onClick = {
                    if (canNavigate) {
                        selectedIndex = index
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}