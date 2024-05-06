package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.Navigate
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

fun NavGraphBuilder.eventGraph(
    navController: NavController
) {
    navigation(
        startDestination = EventNavigationRoute.EventList.fullRoute, // which composable to show first
        route = NavigationGraph.EventGraph.name
    ) {
        composable(
            route = NavigationRoute.Event.route
        ) {
            val eventFormScreen = getThisApplication().eventComponent.eventFormScreen
            eventFormScreen { route -> navController.Navigate(route = route) }
        }
        composable(
            route = EventNavigationRoute.EventList.route
        ) {
            val eventListScreen = getThisApplication().eventComponent.eventListScreen
            eventListScreen { route -> navController.Navigate(route = route) }
        }
    }
}