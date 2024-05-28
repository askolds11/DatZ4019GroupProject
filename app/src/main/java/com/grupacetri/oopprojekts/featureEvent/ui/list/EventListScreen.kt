package com.grupacetri.oopprojekts.featureEvent.ui.list

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
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


    SideEffectComposable(viewModel) {
        when(it) {
            is EventListScreenEvent.SideEffectEvent.NavigateToEventFormCreate -> {
                navigate(EventNavigationRoute.Event(0))
            }
            is EventListScreenEvent.SideEffectEvent.NavigateToEventFormEdit -> {
                navigate(EventNavigationRoute.Event(it.id))
            }
        }
    }

    EventListContent(
        viewModel.state,
        viewModel::onEvent,
    )
}

@Composable
private fun EventListContent(
    state: EventListScreenState,
    onEvent: (EventListScreenEvent) -> Unit,
) {
    LazyColumn {
        items(state.eventList) {
            Row(
                modifier = Modifier
                    .clickable {
                        onEvent(EventListScreenEvent.SideEffectEvent.NavigateToEventFormEdit(it.id))
                    }
            ) {
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
                onClick = { onEvent(EventListScreenEvent.SideEffectEvent.NavigateToEventFormCreate) }
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
        EventListContent(state = state, onEvent = { },)
    }
}
