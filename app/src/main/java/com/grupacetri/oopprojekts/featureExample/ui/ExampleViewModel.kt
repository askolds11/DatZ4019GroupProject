package com.grupacetri.oopprojekts.featureExample.ui

import androidx.lifecycle.ViewModel
import com.grupacetri.oopprojekts.featureExample.di.Example
import me.tatarka.inject.annotations.Inject

@Inject
class ExampleViewModel(
    private val example: Example
) : ViewModel() {
    val exampleVariable = example.doStuff()
}