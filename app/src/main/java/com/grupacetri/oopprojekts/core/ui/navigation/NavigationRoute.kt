package com.grupacetri.oopprojekts.core.ui.navigation

// list of all main navigation routes
sealed class NavigationRoute(val route: String, val args: String? = null) {
    abstract val filledRoute: String
    val fullRoute = route + (args ?: "")
    data object Foo: NavigationRoute(
        route = "Foo"
    ) {
        override val filledRoute
            get() = route
    }
    data object Event: NavigationRoute(
        route = "Event"
    ) {
        override val filledRoute
            get() = route
    }
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
