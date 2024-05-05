package com.grupacetri.oopprojekts.featureEvent.ui.form

sealed class EventFormScreenEvent {
    data class UpdateName(val newValue: String) : EventFormScreenEvent()

    data class UpdateComment(val newValue: String) : EventFormScreenEvent()

    data class UpdateColor(val newValue: String): EventFormScreenEvent()

    data object Save : EventFormScreenEvent()

}