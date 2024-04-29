package com.grupacetri.oopprojekts.core.ui.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.NavigationGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.fooGraph

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavigationGraph.FooGraph.name,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            fooGraph(navController)
        }
    }

}