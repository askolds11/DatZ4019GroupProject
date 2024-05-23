package com.grupacetri.oopprojekts.featureEvent.ui.history

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent


sealed class EventHistoryScreenEvent {
    sealed class SideEffectEvent: BaseSideEffectEvent, EventHistoryScreenEvent() {
        data class NavigateToScreen999(val id: Long): SideEffectEvent()
    }
}