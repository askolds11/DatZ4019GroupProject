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
        val navigate = rememberNavigate(navController)
        val eventHistoryScreen = getThisApplication().historyComponent.historyListScreen
        eventHistoryScreen(navigate)
    }

    composable<HistoryNavigationRoute.HistoryForm> {
        val navigate = rememberNavigate(navController)
        val eventTimeInstanceForm = getThisApplication().historyComponent.historyFormScreen
        eventTimeInstanceForm(navigate)
    }
}