package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureExample.domain.ExampleItem

@Stable
class ExampleScreenState {
    val exampleList = mutableStateListOf<ExampleItem>()
    val inputText = mutableStateOf("")
}