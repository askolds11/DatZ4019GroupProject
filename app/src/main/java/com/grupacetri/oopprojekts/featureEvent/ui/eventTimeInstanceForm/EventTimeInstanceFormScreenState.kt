package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventTimeInstanceFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases

@Stable
class EventTimeInstanceFormScreenState {

    val eventFormItem = mutableStateOf(EventTimeInstanceFormItem(1L,"", ""))

    val nameValidation: MutableState<EventUseCases.EventNameError?> = mutableStateOf(null)
}