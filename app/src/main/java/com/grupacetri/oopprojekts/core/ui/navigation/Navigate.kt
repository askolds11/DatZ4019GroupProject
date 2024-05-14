package com.grupacetri.oopprojekts.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

@Deprecated("Use NavigateToRoute2 instead.")
typealias NavigateToRoute = @Composable (NavigationRoute) -> Unit
typealias NavigateToRoute2 = (NavigationRoute) -> Unit

// this function prevents navigation if you've already clicked on something and are in the
// process of navigating
/**
 * Get whether it is currently possible to navigate
 *
 * @return State of boolean, whether it is currently possible to navigate
 */
@Composable
private fun rememberCanNavigate(): State<Boolean> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val canNavigate = remember(lifecycleState) {
        derivedStateOf {
            lifecycleState.isAtLeast(Lifecycle.State.RESUMED)
        }
    }
    return canNavigate
}

@Composable
@Deprecated("Use the non-composable version")
fun NavController.Navigate(route: NavigationRoute) {
    val navController = remember { this }
    val canNavigate by rememberCanNavigate()

    LaunchedEffect(key1 = Unit) {
        if (canNavigate) {
            navController.navigate(route = route.filledRoute)
        }
    }
}


/**
 * Get navigation function.
 *
 * @param navController navController from [androidx.navigation.compose.rememberNavController]
 * @return Navigation function, that respects other active navigation (can not navigate simultaneously)
 */
@Composable
fun rememberNavigate(navController: NavController): NavigateToRoute2 {
    val canNavigate by rememberCanNavigate()

    return remember(canNavigate) {
        { route ->
            navController.navigate(route, canNavigate)
        }
    }
}

/**
 * Navigate to a destination, if canNavigate is true.
 *
 * For canNavigate see [rememberNavigate]
 * @param route route, to which to navigate
 * @param canNavigate is navigation possible at this moment
 */
private fun NavController.navigate(route: NavigationRoute, canNavigate: Boolean) {
    if (canNavigate) {
        this.navigate(route.filledRoute)
    }
}