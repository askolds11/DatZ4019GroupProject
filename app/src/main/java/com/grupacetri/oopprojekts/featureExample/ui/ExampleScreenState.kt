package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureExample.domain.ExampleItem

// all data in screen
// when changed, the composables see it, and recompose if changed
// the type needs to be State for this to work! e.g. simply inputText = "text" would not cause
// the composables to update.
@Stable
class ExampleScreenState {
    // SnapshotStateList - triggers an update when inserting/updating/deleting in the list.
    // if it was a mutableStateOf a list, you'd need to change the list to a different instance (copy)
    // each time to update it.
    val exampleList = mutableStateListOf<ExampleItem>()
    val inputText = mutableStateOf("")
}