package com.grupacetri.oopprojekts.featureEvent.ui.create

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureEvent.domain.EventFormItem

@Stable
class EventFormScreenState {
    val eventFormItem = mutableStateOf(EventFormItem(1L, "nosaukums", "oogabooga", "#000000", true,
        "before the beginning of time", "after the end of time"))
}