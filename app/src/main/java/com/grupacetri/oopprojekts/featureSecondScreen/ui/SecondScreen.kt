package com.grupacetri.oopprojekts.featureSecondScreen.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.ui.theme.OOPProjektsTheme
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

// for dependency injection - skip dependencies in declaration
typealias SecondScreen = @Composable (navigate: (NavigationRoute) -> Unit) -> Unit

// screen composable - dependency inject here
// basically a wrapper for content, so you can dependency inject and still easily preview
@Inject
@Composable
fun SecondScreen(
    secondViewModel: () -> SecondViewModel,
    @Assisted navigate: (NavigationRoute) -> Unit
) {
    val viewModel = viewModel { secondViewModel() }
    SecondContent(
        viewModel.state,
        viewModel::onEvent,
        navigate
    )
}

@Composable
private fun SecondContent(
    state: SecondScreenState,
    onEvent: (SecondScreenEvent) -> Unit,
    navigate: (NavigationRoute) -> Unit
) {
    // column for list - LazyColumn means it only shows the items on screen
    // and doesn't have "hidden items" offscreen - much more performant
    LazyColumn {
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
                        onEvent(SecondScreenEvent.UpdateText(it)) // when writing, call this function
                    },
                    label = {
                        Text("Custom String")
                    }
                )
            }
        }
        item {
            Button(onClick = { navigate(NavigationRoute.Example1) }) {
                Text("Navigate to example1")
            }
        }
    }
}

// preview the composable
// does not require the emulator to be running, gives an idea of how the screen will look
// not setup fully - can make it look like a device for more accurate previews.
@Preview
@Composable
private fun SecondContentPreview() {
    OOPProjektsTheme {
        val state = SecondScreenState()
        state.inputText.value = "ooga boog..."
        SecondContent(state = state, onEvent = { }, navigate = { })
    }
}