package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tatarka.inject.annotations.Inject

@Inject
@Composable
fun ExampleScreen(
    exampleViewModel: () -> ExampleViewModel
) {
    val viewModel = viewModel { exampleViewModel() }
    Text("Stuff!")
}