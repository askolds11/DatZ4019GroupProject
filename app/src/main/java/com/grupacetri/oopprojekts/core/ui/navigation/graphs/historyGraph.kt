package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.Navigate
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

fun NavGraphBuilder.historyGraph(
    navController: NavController
) {
    navigation(
        startDestination = HistoryNavigationRoute.HistoryList.fullRoute, // which composable to show first
        route = NavigationGraph.HistoryGraph.name
    ) {
        composable(
            route = HistoryNavigationRoute.HistoryList.route
        ) {
            val eventHistoryScreen = getThisApplication().eventComponent.eventHistoryScreen
            eventHistoryScreen { route -> navController.Navigate(route = route) }
        }
    }
}