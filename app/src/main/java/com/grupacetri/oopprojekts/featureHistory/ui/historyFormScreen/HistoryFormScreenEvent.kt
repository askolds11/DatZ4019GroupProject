package com.grupacetri.oopprojekts.featureHistory.ui.historyFormScreen

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent

sealed class HistoryFormScreenEvent {
    data class UpdateTimeStarted(val newValue: String) : HistoryFormScreenEvent()
    data class UpdateTimeEnded(val newValue: String) : HistoryFormScreenEvent()
    data object Save : HistoryFormScreenEvent()
    sealed class SideEffectEvent: BaseSideEffectEvent, HistoryFormScreenEvent() {
        data object NavigateBack: SideEffectEvent()
    }

}