package com.grupacetri.oopprojekts.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.grupacetri.oopprojekts.OopProjektsApplication
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// this file contains some helper functions

@Composable
fun Flow<Unit>.collectAsStateWithLifecycle(): State<Unit> {
    // since we save state in a single class, we have a flow that updates it
    // however to save resources, the flow updates the state, and returns Unit
    // this is a helper functions so you don't have to write (initialValue = Unit) each time
    return this.collectAsStateWithLifecycle(initialValue = Unit)
}

fun Context.getThisApplication(): OopProjektsApplication {
    return applicationContext as OopProjektsApplication
}

@Composable
fun getThisApplication(): OopProjektsApplication {
    return LocalContext.current.getThisApplication()
}

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

// this function navigates to a screen
fun NavController.navigate(route: NavigationRoute/*, canNavigate: Boolean*/) {
//    if (canNavigate) {
        this.navigate(route = route.filledRoute)
//    }
}