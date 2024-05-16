package com.grupacetri.oopprojekts.featureFoo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureFoo.domain.FooItem
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias FooScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun FooScreen(
    fooViewModel: (SavedStateHandle) -> FooViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { fooViewModel(createSavedStateHandle()) }
    viewModel.fooListFlow.collectAsStateWithLifecycle()

    SideEffectComposable(viewModel) {
        when(it) {
            FooScreenEvent.SideEffectEvent.NavigateToScreen999 -> {
                navigate(FooNavigationRoute.Foo)
            }
        }
    }

    ExampleContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@Composable
private fun ExampleContent(
    state: FooScreenState,
    onEvent: (FooScreenEvent) -> Unit
) {
    LazyColumn {
        items(state.fooList) {
            Row {
                Text(it.foo ?: "null lol")
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onEvent(FooScreenEvent.Delete(it.fooOrig)) }
                ) {
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
                        onEvent(FooScreenEvent.UpdateText(it))
                    },
                    label = {
                        Text("Custom String")
                    }
                )
                Button(onClick = { onEvent(FooScreenEvent.Save) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Save"
                    )
                }
            }
        }
        item {
            Button(onClick = {
                onEvent(FooScreenEvent.SideEffectEvent.NavigateToScreen999)
            }) {
                Text(text = state.randomText.value)
            }
        }
    }
}

@Preview
@Composable
private fun ExampleContentPreview() {
    OOPProjektsTheme {
        val state = FooScreenState()
        state.fooList.addAll(listOf(
            FooItem(0, "Item 1"),
            FooItem(1, "Random"),
            FooItem(2, "abbdb"),
        ))
        ExampleContent(state = state, onEvent = { })
    }
}