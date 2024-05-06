package com.grupacetri.oopprojekts.core.ui.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.eventGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.fooGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.historyGraph

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = FooNavigationRoute.Foo,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            fooGraph(navController)
            eventGraph(navController)
            historyGraph(navController)
        }
    }
}