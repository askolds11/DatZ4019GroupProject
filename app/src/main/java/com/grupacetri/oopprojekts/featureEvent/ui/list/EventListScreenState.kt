package com.grupacetri.oopprojekts.featureEvent.ui.list

import androidx.compose.runtime.mutableStateListOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventItem

class EventListScreenState {
    val eventList = mutableStateListOf<EventItem>()
}