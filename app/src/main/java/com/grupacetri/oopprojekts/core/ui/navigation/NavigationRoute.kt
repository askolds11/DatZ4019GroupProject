package com.grupacetri.oopprojekts.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data object NavigateUp: NavigationRoute()
}

// for each section (bottom app bar item) define a new NavigationRoute sealed class
@Serializable
sealed class FooNavigationRoute: NavigationRoute() {
    @Serializable
    data object Foo: FooNavigationRoute()
}

@Serializable
sealed class EventNavigationRoute : NavigationRoute() {
    @Serializable
    data class Event(val id: Long): NavigationRoute()
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
