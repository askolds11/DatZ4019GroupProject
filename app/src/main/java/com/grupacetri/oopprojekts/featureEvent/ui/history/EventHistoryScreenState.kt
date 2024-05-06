package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.compose.runtime.mutableStateListOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventHistoryItem
import com.grupacetri.oopprojekts.featureEvent.domain.EventItem

class EventHistoryScreenState {
    val eventHistoryList = mutableStateListOf<EventHistoryItem>()
}