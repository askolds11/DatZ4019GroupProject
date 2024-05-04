package com.grupacetri.oopprojekts.featureEvent.ui.create

import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent

sealed class EventFormScreenEvent {
    data class UpdateName(val newValue: String) : EventFormScreenEvent()

    data class UpdateComment(val newValue: String) : EventFormScreenEvent()

    data class UpdateColor(val newValue: String): EventFormScreenEvent()

}