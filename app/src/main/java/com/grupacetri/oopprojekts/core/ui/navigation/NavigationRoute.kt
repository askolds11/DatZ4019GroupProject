package com.grupacetri.oopprojekts.core.ui.navigation

import kotlinx.serialization.Serializable
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

@Serializable
sealed class NavigationRoute

// for each section (bottom app bar item) define a new NavigationRoute sealed class
@Serializable
sealed class FooNavigationRoute: NavigationRoute() {
    @Serializable
    data object Foo: FooNavigationRoute()
}// later define navigation routes in graphs e.g. FooNavigationRoute
sealed class EventNavigationRoute(route: String, args: String? = null) : NavigationRoute(route, args) {
    data object EventList: NavigationRoute(
        route = "EventList"
    ) {
        override val filledRoute
            get() = route
    }
}
