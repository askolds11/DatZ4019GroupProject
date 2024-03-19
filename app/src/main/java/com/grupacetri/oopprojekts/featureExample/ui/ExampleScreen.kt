package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import me.tatarka.inject.annotations.Inject

typealias ExampleScreen = @Composable () -> Unit

@Inject
@Composable
fun ExampleScreen(
    exampleViewModel: () -> ExampleViewModel
) {
    val viewModel = viewModel { exampleViewModel() }
    LazyColumn {
        items(viewModel.exampleList) {
            Text(it)
        }
    }
}