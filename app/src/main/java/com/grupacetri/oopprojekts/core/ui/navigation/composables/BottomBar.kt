package com.grupacetri.oopprojekts.core.ui.navigation.composables

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

// all required data for the bottom bar destinations
// sealed class just means that you can't modify it at runtime
// everything is known at compile time
private sealed class TopScreen(
    val route: String,
    val iconInactive: ImageVector,
    val iconActive: ImageVector,
    @StringRes val resourceId: Int
) {
    data object Example1 : TopScreen(
        NavigationRoute.Example1.route,
        Icons.Outlined.Home,
        Icons.Filled.Home,
        R.string.example1
    )
}
@Composable
fun BottomBar(
    navController: NavController,
) {
    val items = remember {
        listOf(
            TopScreen.Example1
        )
    }
    BottomAppBar() {
        // don't really need to understand this - this is code directly from android documentation
        // and you only need to change the items list to add new destinations
        NavigationBar(
            tonalElevation = 0.dp,
        ) {
            // see https://developer.android.com/jetpack/compose/navigation#bottom-nav
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                NavigationBarItem(
                    icon = {
                        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                            Icon(screen.iconActive, contentDescription = null)
                        } else {
                            Icon(screen.iconInactive, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = stringResource(screen.resourceId))
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
}