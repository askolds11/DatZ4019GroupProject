package com.grupacetri.oopprojekts.core.ui.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.grupacetri.oopprojekts.core.getThisApplication
import com.grupacetri.oopprojekts.core.ui.navigation.SettingsNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.rememberNavigate

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    composable<SettingsNavigationRoute.SettingsList> {
        val navigate = rememberNavigate(navController = navController)
        
        val settingsScreen = getThisApplication().settingsComponent.settingsScreen
        settingsScreen(navigate)
    }
}