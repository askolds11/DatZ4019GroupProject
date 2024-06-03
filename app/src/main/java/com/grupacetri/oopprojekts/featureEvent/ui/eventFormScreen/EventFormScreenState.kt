package com.grupacetri.oopprojekts.featureEvent.ui.eventFormScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases

@Stable
class EventFormScreenState {
    val eventFormItem = mutableStateOf(
        EventFormItem(
            1L,
            "",
            null,
            "",
            active = true,
            started = false,
            created = "",
            modified = ""
        )
    )
    val nameValidation: MutableState<EventUseCases.EventNameError?> = mutableStateOf(null)
    val colorValidation: MutableState<EventUseCases.EventColorError?> = mutableStateOf(null)
    val saveEnabled = mutableStateOf(false)
    val isEditMode = mutableStateOf(false)
}