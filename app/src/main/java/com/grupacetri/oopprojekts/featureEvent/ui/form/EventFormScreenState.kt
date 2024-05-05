package com.grupacetri.oopprojekts.featureEvent.ui.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases

@Stable
class EventFormScreenState {
    val eventFormItem = mutableStateOf(EventFormItem(1L, "", null, "", true,
        "before the beginning of time", "after the end of time"))

    val nameValidation: MutableState<EventUseCases.EventNameError?> = mutableStateOf(null)
}