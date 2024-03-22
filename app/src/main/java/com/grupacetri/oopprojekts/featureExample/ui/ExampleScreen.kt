package com.grupacetri.oopprojekts.featureExample.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.featureExample.domain.ExampleItem
import com.grupacetri.oopprojekts.ui.theme.OOPProjektsTheme
import me.tatarka.inject.annotations.Inject

// for dependency injection - skip dependencies in declaration
typealias ExampleScreen = @Composable (navigate: (NavigationRoute) -> Unit) -> Unit

// screen composable - dependency inject here
// basically a wrapper for content, so you can dependency inject and still easily preview
@Inject
@Composable
fun ExampleScreen(
    exampleViewModel: () -> ExampleViewModel,
    navigate: (NavigationRoute) -> Unit
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
    // column for list - LazyColumn means it only shows the items on screen
    // and doesn't have "hidden items" offscreen - much more performant
    LazyColumn {
        // for each item in state.exampleList do this
        items(state.exampleList) {
            // row so items stack horizontally
            Row {
                Text(it.customString)
                Button(
                    onClick = { onEvent(ExampleScreenEvent.Delete(it.id)) }
                ) {
                    // content inside of button
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
        // a standalone item. needs to be wrapped in item {} so it can be "not drawn" when offscreen.
        item {
            Row {
                // input box
                // !!! - it literally shows the value state.inputText.value.
                // when text (a character, pasted, etc) is inputted,
                // it calls the onValueChange function, which changes the state.inputText.value,
                // which causes the TextField to change the shown value.
                // if onValueChang was empty, anything you write would not show up.
                TextField(
                    value = state.inputText.value, // show this value
                    onValueChange = {
                        onEvent(ExampleScreenEvent.UpdateText(it)) // when writing, call this function
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

// preview the composable
// does not require the emulator to be running, gives an idea of how the screen will look
// not setup fully - can make it look like a device for more accurate previews.
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