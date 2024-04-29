package com.grupacetri.oopprojekts.featureFoo.ui

import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute

sealed class FooScreenEvent {
    data class Delete(val foo: Long?): FooScreenEvent()
    data class UpdateText(val newValue: String): FooScreenEvent()
    data object Save: FooScreenEvent()
    data class NavigateToRoute(val route: NavigationRoute?): FooScreenEvent()
}