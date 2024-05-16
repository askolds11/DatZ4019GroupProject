package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.rememberNavigate

fun NavGraphBuilder.fooGraph(
    navController: NavController
) {
    composable<FooNavigationRoute.Foo> {
        val navigate = rememberNavigate(navController)

        val fooScreen = getThisApplication().fooComponent.fooScreen
        fooScreen(navigate)
    }
}