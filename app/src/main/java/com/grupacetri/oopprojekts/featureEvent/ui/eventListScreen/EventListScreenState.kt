package com.grupacetri.oopprojekts.featureEvent.ui.eventListScreen

import androidx.compose.runtime.mutableStateListOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventItem

class EventListScreenState {
    val eventList = mutableStateListOf<EventItem>()
    val startedEventList = mutableStateListOf<EventItem>()
    val inactiveEventList = mutableStateListOf<EventItem>()
}