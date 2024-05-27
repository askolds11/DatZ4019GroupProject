package com.grupacetri.oopprojekts.featureEvent.ui.form

import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent
import com.grupacetri.oopprojekts.featureEvent.ui.list.EventListScreenEvent

sealed class EventFormScreenEvent {
    data class UpdateName(val newValue: String) : EventFormScreenEvent()

    data class UpdateComment(val newValue: String) : EventFormScreenEvent()

    data class UpdateColor(val newValue: String): EventFormScreenEvent()

    data object Save : EventFormScreenEvent()

    sealed class SideEffectEvent: BaseSideEffectEvent, EventFormScreenEvent() {
        data object NavigateUp: SideEffectEvent()
    }


}