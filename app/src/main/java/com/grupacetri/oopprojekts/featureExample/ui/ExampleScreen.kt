package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.ui.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.featureExample.domain.ExampleItem
import com.grupacetri.oopprojekts.ui.theme.OOPProjektsTheme
import me.tatarka.inject.annotations.Inject

typealias ExampleScreen = @Composable () -> Unit

@Inject
@Composable
fun ExampleScreen(
    exampleViewModel: () -> ExampleViewModel
) {
    val viewModel = viewModel { exampleViewModel() }
    viewModel.exampleListFlow.collectAsStateWithLifecycle()
    ExampleContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@Composable
private fun ExampleContent(
    state: ExampleScreenState,
    onEvent: (ExampleScreenEvent) -> Unit
) {
    LazyColumn {
        items(state.exampleList) {
            Row {
                Text(it.customString)
                Button(onClick = { onEvent(ExampleScreenEvent.Delete(it.id)) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
        item {
            Row {
                TextField(
                    value = state.inputText.value,
                    onValueChange = {
                        onEvent(ExampleScreenEvent.UpdateText(it))
                    },
                    label = {
                        Text("Custom String")
                    }
                )
                Button(onClick = { onEvent(ExampleScreenEvent.Save) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Save"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExampleContentPreview() {
    OOPProjektsTheme {
        val state = ExampleScreenState()
        state.exampleList.addAll(listOf(
            ExampleItem(0, "Item 1"),
            ExampleItem(1, "Random"),
            ExampleItem(2, "abbdb"),
        ))
        ExampleContent(state = state, onEvent = { })
    }
}