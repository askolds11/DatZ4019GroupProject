package com.grupacetri.oopprojekts.featureFoo.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureFoo.domain.FooItem

@Stable
class FooScreenState {
    val fooList = mutableStateListOf<FooItem>()
    val inputText = mutableStateOf("")
}