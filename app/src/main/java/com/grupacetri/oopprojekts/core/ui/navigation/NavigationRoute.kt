package com.grupacetri.oopprojekts.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute

// for each section (bottom app bar item) define a new NavigationRoute sealed class
@Serializable
sealed class FooNavigationRoute: NavigationRoute() {
    @Serializable
    data object Foo: FooNavigationRoute()
}

@Serializable
sealed class EventNavigationRoute : NavigationRoute() {
    @Serializable
    data object Event: NavigationRoute()
    @Serializable
    data object EventList: NavigationRoute()
}

sealed class HistoryNavigationRoute(route: String, args: String? = null) : NavigationRoute(route, args) {
    data object HistoryList: NavigationRoute(
        route = "HistoryList"
    ) {
        override val filledRoute
            get() = route
    }
}
