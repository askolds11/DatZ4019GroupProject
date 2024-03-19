package com.grupacetri.oopprojekts.featureExample.ui

import androidx.lifecycle.ViewModel
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import me.tatarka.inject.annotations.Inject

@Inject
class ExampleViewModel(
    private val exampleUseCases: ExampleUseCases
) : ViewModel() {
    val exampleList = exampleUseCases.getList()
    val example = exampleUseCases.get(1)
}