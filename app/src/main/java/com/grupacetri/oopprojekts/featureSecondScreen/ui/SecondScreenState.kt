package com.grupacetri.oopprojekts.featureSecondScreen.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf

// all data in screen
// when changed, the composables see it, and recompose if changed
// the type needs to be State for this to work! e.g. simply inputText = "text" would not cause
// the composables to update.
@Stable
class SecondScreenState {
    val inputText = mutableStateOf("")
}