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

typealias NavigateToRoute = @Composable (NavigationRoute) -> Unit

// this function prevents navigation if you've already clicked on something and are in the
// process of navigating
@Composable
fun rememberCanNavigate(): State<Boolean> {
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
fun NavController.Navigate(route: NavigationRoute) {
    val navController = remember { this }
    val canNavigate by rememberCanNavigate()

    LaunchedEffect(key1 = Unit) {
        if (canNavigate) {
            navController.navigate(route = route.filledRoute)
        }
    }
}