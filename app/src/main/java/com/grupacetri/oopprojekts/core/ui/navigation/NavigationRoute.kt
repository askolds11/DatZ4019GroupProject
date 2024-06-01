package com.grupacetri.oopprojekts.core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data object NavigateUp: NavigationRoute()
}
// list of all main navigation routes


// for each section (bottom app bar item) define a new NavigationRoute sealed class
@Serializable
sealed class EventNavigationRoute : NavigationRoute() {
    @Serializable
    data class Event(val id: Long): NavigationRoute()
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
@Serializable
sealed class SettingsNavigationRoute: NavigationRoute() {
    @Serializable
    data object SettingsList: NavigationRoute()
}