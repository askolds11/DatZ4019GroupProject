package com.grupacetri.oopprojekts.featureEvent.ui.eventFormScreen

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent

sealed class EventFormScreenEvent {
    data class UpdateName(val newValue: String) : EventFormScreenEvent()
    data class UpdateComment(val newValue: String) : EventFormScreenEvent()
    data class UpdateColor(val newValue: String) : EventFormScreenEvent()
    data class UpdateActive(val newValue: Boolean) : EventFormScreenEvent()
    data object Save : EventFormScreenEvent()
    sealed class SideEffectEvent : BaseSideEffectEvent, EventFormScreenEvent() {
        data object NavigateUp : SideEffectEvent()
    }


}