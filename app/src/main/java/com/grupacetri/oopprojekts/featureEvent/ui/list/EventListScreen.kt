package com.grupacetri.oopprojekts.featureEvent.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias EventListScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun EventListScreen(
    eventViewModel: () -> EventListScreenViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { eventViewModel() }
    viewModel.settingsFlow.collectAsStateWithLifecycle()

    // clear navigation
//    LaunchedEffect(Unit) {
//        viewModel.onEvent(EventListScreenEvent.NavigateToRoute2(null))
//    }

    EventListContent(
        viewModel.state,
        viewModel::onEvent,
        navigate
    )
}

@Composable
private fun EventListContent(
    state: EventListScreenState,
    onEvent: (EventListScreenEvent) -> Unit,
    navigate: NavigateToRoute2
) {
    var nav_var by remember {
        mutableStateOf(false)
    }
    if (nav_var) {
        navigate(EventNavigationRoute.Event)
    }
    LazyColumn {
        items(state.eventList) {
            Row {
                Text(it.name)
                Spacer(modifier = Modifier.weight(1f))
                if (it.started) {
                    Button(
                        onClick = { onEvent(EventListScreenEvent.StopTracking(it.id)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Track"
                        )
                    }
                }
                else {
                    Button(
                        onClick = { onEvent(EventListScreenEvent.StartTracking(it.id)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Track"
                        )
                    }
                }
            }
        }
        item{
            Button(
                onClick = { nav_var = true }
            ) {
                Icon (
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
            }
        }
        items(state.startedEventList) {
            Row {
                Text(it.name)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onEvent(EventListScreenEvent.StopTracking(it.id)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Track"
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
        val state = EventListScreenState()
        state.eventList.addAll(listOf(
//            EventItem(0, "Item 1", "#000000", true),
//            EventItem(1, "Random","#000000", false),
//            EventItem(2, "abbdb","#000000", true),
        ))
        EventListContent(state = state, onEvent = { }, navigate = { })
    }
}
