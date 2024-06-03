package com.grupacetri.oopprojekts.featureHistory.ui.historyListScreen

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent


sealed class HistoryListScreenEvent {
    data object PreviousDay: HistoryListScreenEvent()
    data object NextDay: HistoryListScreenEvent()
    sealed class SideEffectEvent: BaseSideEffectEvent, HistoryListScreenEvent() {
        data class NavigateToForm(val id: Long): SideEffectEvent()
    }
}