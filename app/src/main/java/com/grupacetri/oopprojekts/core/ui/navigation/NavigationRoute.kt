package com.grupacetri.oopprojekts.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute
// list of all main navigation routes


// for each section (bottom app bar item) define a new NavigationRoute sealed class
@Serializable
sealed class FooNavigationRoute: NavigationRoute() {
    @Serializable
    data object Foo: FooNavigationRoute()
}
// later define navigation routes in graphs e.g. FooNavigationRoute
sealed class EventNavigationRoute(route: String, args: String? = null) : NavigationRoute(route, args) {
    data object EventList: NavigationRoute(
        route = "EventList"
    ) {
        override val filledRoute
            get() = route
    }
}

sealed class HistoryNavigationRoute(route: String, args: String? = null) : NavigationRoute(route, args) {
    data object HistoryList: NavigationRoute(
        route = "HistoryList"
    ) {
        override val filledRoute
            get() = route
    }
}
