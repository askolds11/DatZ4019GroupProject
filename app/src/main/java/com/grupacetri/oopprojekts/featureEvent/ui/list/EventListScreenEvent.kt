package com.grupacetri.oopprojekts.featureEvent.ui.list



sealed class EventListScreenEvent {
    data class StartTracking (val id: Long) : EventListScreenEvent()

    data class StopTracking (val id: Long) : EventListScreenEvent()

}