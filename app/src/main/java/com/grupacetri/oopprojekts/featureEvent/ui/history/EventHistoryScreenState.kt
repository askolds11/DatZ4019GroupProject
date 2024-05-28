package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.compose.runtime.mutableStateListOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventHistoryItem

class EventHistoryScreenState {
    val eventHistoryList = mutableStateListOf<EventHistoryItem>()
}