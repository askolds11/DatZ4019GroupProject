package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class EventHistoryScreenState {
    val eventHistoryList = mutableStateListOf<EventHistoryUiItem>()
    val date = mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
}

