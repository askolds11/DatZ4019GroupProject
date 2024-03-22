package com.grupacetri.oopprojekts.core.ui.navigation

// list of all navigation routes
sealed class NavigationRoute(val route: String, val args: String? = null) {
    abstract val filledRoute: String
    val fullRoute = route + (args ?: "")
    data object Example1: NavigationRoute(
        route = "EXAMPLE1"
    ) {
        override val filledRoute
            get() = route
    }
}