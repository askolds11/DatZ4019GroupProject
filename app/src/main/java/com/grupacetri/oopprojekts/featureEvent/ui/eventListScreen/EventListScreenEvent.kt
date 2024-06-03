package com.grupacetri.oopprojekts.featureEvent.ui.eventListScreen

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent


sealed class EventListScreenEvent {
    data class StartTracking (val id: Long) : EventListScreenEvent()

    data class StopTracking (val id: Long) : EventListScreenEvent()
    sealed class SideEffectEvent: BaseSideEffectEvent, EventListScreenEvent() {
        data object NavigateToEventFormCreate: SideEffectEvent()
        data class NavigateToEventFormEdit(val id: Long): SideEffectEvent()
    }
}