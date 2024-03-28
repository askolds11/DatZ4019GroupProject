package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.grupacetri.oopprojekts.core.ui.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigate
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

// graph for the example screen
// you generally want a graph for pretty separate things in your app
// e.g. a navigation graph for each bottom bar destination
fun NavGraphBuilder.secondScreenGraph(
    navController: NavController
) {
    navigation(
        startDestination = NavigationRoute.SecondScreen.fullRoute, // which composable to show first
        route = NavigationGraph.SecondScreen.name
    ) {
        // a destination
        composable(
            route = NavigationRoute.SecondScreen.route
        ) {
            // get the composable - requires dependency injection
            // if there was no dependency injection, you could simply call ExampleScreen()
            val secondScreen = LocalContext.current.getThisApplication().uiComponent.secondScreen
            // pass the navigation function, so you can go to different destinations from the screen
            secondScreen(navController::navigate)
        }
    }
}