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
fun NavGraphBuilder.example1Graph(
    navController: NavController
) {
    navigation(
        startDestination = NavigationRoute.Example1.fullRoute, // which composable to show first
        route = NavigationGraph.example.name
    ) {
        // a destination
        composable(
            route = NavigationRoute.Example1.route
        ) {
            // get the composable - requires dependency injection
            // if there was no dependency injection, you could simply call ExampleScreen()
            val exampleScreen = LocalContext.current.getThisApplication().exampleComponent.exampleScreen
            // pass the navigation function, so you can go to different destinations from the screen
            exampleScreen(navController::navigate)
        }
    }
}