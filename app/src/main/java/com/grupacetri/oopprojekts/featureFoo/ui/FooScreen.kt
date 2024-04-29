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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute
import com.grupacetri.oopprojekts.featureFoo.domain.FooItem
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias FooScreen = @Composable (navigate: NavigateToRoute) -> Unit

@Inject
@Composable
fun FooScreen(
    fooViewModel: () -> FooViewModel,
    @Assisted navigate: NavigateToRoute
) {
    val viewModel = viewModel { fooViewModel() }
    viewModel.fooListFlow.collectAsStateWithLifecycle()

    // clear navigation
    LaunchedEffect(Unit) {
        viewModel.onEvent(FooScreenEvent.NavigateToRoute(null))
    }

    ExampleContent(
        viewModel.state,
        viewModel::onEvent,
        navigate
    )
}

@Composable
private fun ExampleContent(
    state: FooScreenState,
    onEvent: (FooScreenEvent) -> Unit,
    navigate: NavigateToRoute
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
        ExampleContent(state = state, onEvent = { }, navigate = { })
    }
}