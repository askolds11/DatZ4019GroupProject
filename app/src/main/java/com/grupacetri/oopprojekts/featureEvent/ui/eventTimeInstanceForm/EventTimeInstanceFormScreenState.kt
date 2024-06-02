package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventTimeInstanceFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases

@Stable
class EventTimeInstanceFormScreenState {

    val eventTimeInstanceFormItem = mutableStateOf(EventTimeInstanceFormItem(1L,0, "", "", "", ""))

    val timeStartedValidation: MutableState<EventUseCases.EventTimeError?> = mutableStateOf(null)
    val timeEndedValidation: MutableState<EventUseCases.EventTimeError?> = mutableStateOf(null)

    val saveEnabled = mutableStateOf(false)
}
