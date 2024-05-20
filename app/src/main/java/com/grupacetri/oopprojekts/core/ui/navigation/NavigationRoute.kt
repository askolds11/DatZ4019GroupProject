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
@Serializable
sealed class EventNavigationRoute : NavigationRoute() {
    @Serializable
    data object Event: NavigationRoute()
    @Serializable
    data object EventList: NavigationRoute()
}
@Serializable
sealed class HistoryNavigationRoute : NavigationRoute() {
    @Serializable
    data object HistoryList: NavigationRoute()
    @Serializable
    data class EventTimeInstanceForm(val id: Long): NavigationRoute()
}
