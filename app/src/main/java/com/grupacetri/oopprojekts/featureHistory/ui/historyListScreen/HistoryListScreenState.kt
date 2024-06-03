package com.grupacetri.oopprojekts.featureHistory.ui.historyListScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryListScreenState {
    val eventHistoryList = mutableStateListOf<HistoryListUiItem>()
    val timeDiffFormat = mutableStateOf(AllSettings.TimeDiffFormat())
    val date = mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
}

