package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.Navigate
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

fun NavGraphBuilder.fooGraph(
    navController: NavController
) {
    navigation(
        startDestination = NavigationRoute.Foo.fullRoute, // which composable to show first
        route = NavigationGraph.FooGraph.name
    ) {
        composable(
            route = NavigationRoute.Foo.route
        ) {
            val fooScreen = getThisApplication().fooComponent.fooScreen
            fooScreen { route -> navController.Navigate(route = route) }
        }
    }
}