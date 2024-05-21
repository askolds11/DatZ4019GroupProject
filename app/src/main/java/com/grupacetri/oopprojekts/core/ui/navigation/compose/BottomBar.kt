package com.grupacetri.oopprojekts.core.ui.navigation.compose

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

// Add icons to top level (NavigationRoute) destinations
private sealed class TopScreen(
    val route: NavigationRoute,
    val iconInactive: ImageVector,
    val iconActive: ImageVector,
    @StringRes val resourceId: Int
) {
    data object Foo : TopScreen(
        FooNavigationRoute.Foo,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.foo
    )

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
}

@Composable
fun BottomBar(
    navController: NavController,
) {
    val items = remember {
        listOf(
            TopScreen.Foo,
            TopScreen.Event,
            TopScreen.History
        )
    }
    NavigationBar(
        tonalElevation = 0.dp,
    ) {
        // see https://developer.android.com/jetpack/compose/navigation#bottom-nav
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    if (currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true) {
                        Icon(screen.iconActive, contentDescription = null)
                    } else {
                        Icon(screen.iconInactive, contentDescription = null)
                    }
                },
                label = {
                    Text(text = stringResource(screen.resourceId))
                },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true,
                onClick = {
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
            )
        }
    }
}