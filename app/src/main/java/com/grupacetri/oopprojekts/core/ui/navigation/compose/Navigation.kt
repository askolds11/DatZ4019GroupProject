package com.grupacetri.oopprojekts.core.ui.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.eventGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.historyGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.settingsGraph

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = EventNavigationRoute.EventList,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            eventGraph(navController)
            historyGraph(navController)
            settingsGraph(navController)
        }
    }
}