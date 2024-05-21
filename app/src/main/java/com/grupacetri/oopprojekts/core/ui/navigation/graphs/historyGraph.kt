package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.rememberNavigate

fun NavGraphBuilder.historyGraph(
    navController: NavController
) {
    composable<HistoryNavigationRoute.HistoryList> {
        val navigate = rememberNavigate(navController = navController)

        val eventHistoryScreen = getThisApplication().eventComponent.eventHistoryScreen
        eventHistoryScreen(navigate)
    }
}