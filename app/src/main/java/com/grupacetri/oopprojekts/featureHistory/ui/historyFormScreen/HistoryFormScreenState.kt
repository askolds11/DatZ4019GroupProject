package com.grupacetri.oopprojekts.featureHistory.ui.historyFormScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryFormItem
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryUseCases

@Stable
class HistoryFormScreenState {
    val historyFormItem = mutableStateOf(HistoryFormItem(1L, 0, "", "", "", ""))
    val timeStartedValidation: MutableState<HistoryUseCases.EventTimeError?> = mutableStateOf(null)
    val timeEndedValidation: MutableState<HistoryUseCases.EventTimeError?> = mutableStateOf(null)
    val saveEnabled = mutableStateOf(false)
}
