package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent

sealed class EventTimeInstanceFormEvent {
    data class UpdateTimeStarted(val newValue: String) : EventTimeInstanceFormEvent()

    data class UpdateTimeEnded(val newValue: String) : EventTimeInstanceFormEvent()

    data object Save : EventTimeInstanceFormEvent()

    sealed class SideEffectEvent: BaseSideEffectEvent, EventTimeInstanceFormEvent() {
        data object NavigateToScreen999: EventTimeInstanceFormEvent()
    }

}