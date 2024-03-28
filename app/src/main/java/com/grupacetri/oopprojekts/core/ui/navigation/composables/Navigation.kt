package com.grupacetri.oopprojekts.core.ui.navigation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.NavigationGraph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.example1Graph
import com.grupacetri.oopprojekts.core.ui.navigation.graphs.secondScreenGraph

// set up navigation here
@Composable
fun Navigation() {
    val navController = rememberNavController()
    // scaffold basically sets up the bottom navigation bar for you
    // it can also have a top app bar and a floating action button
    // if you need to change them on each screen it's kind of a bother for this reason -
    // you somehow need to update them here.
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {// the scaffold passes padding values that are equal to the top bar
        // this ensures that you don't draw under them and the user can't see the content.
        NavHost(
            navController = navController,
            startDestination = NavigationGraph.Example.name, // first graph to use - uses it's first destination
            modifier = Modifier // modifiers basically set up how the composable looks
                // use the scaffold padding here. this is not recommended,
                // you should pass it into your composable so you can set it up properly there
                .padding(it)
                .fillMaxSize() //basically fill the whole screen
        ) {
            // this just sets up all the graphs - the order doesn't matter, the one that's shown on app start
            // is the one defined in startDestination
            example1Graph(navController)
            secondScreenGraph(navController)
        }
    }
}