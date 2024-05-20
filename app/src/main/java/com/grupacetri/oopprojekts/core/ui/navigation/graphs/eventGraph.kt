package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.rememberNavigate

fun NavGraphBuilder.eventGraph(
    navController: NavController
) {

    composable<EventNavigationRoute.Event> {
        val navigate = rememberNavigate(navController)
        val eventFormScreen = getThisApplication().eventComponent.eventFormScreen
        eventFormScreen(navigate)
    }
    composable<EventNavigationRoute.EventList> {
        val navigate = rememberNavigate(navController)
        val eventListScreen = getThisApplication().eventComponent.eventListScreen
        eventListScreen(navigate)
    }
}