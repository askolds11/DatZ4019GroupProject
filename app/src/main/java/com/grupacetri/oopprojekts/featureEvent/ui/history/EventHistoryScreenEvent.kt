package com.grupacetri.oopprojekts.featureEvent.ui.history

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent


sealed class EventHistoryScreenEvent {
    sealed class SideEffectEvent: BaseSideEffectEvent, EventHistoryScreenEvent() {
        data class NavigateToForm(val id: Long): SideEffectEvent()
    }
}