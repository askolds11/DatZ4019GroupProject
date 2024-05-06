package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureEvent.domain.EventItem
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

typealias EventHistoryScreen = @Composable (navigate: NavigateToRoute) -> Unit

@Inject
@Composable
fun EventHistoryScreen(
    eventViewModel: () -> EventHistoryScreenViewModel,
    @Assisted navigate: NavigateToRoute
) {
    val viewModel = viewModel { eventViewModel() }
    viewModel.eventHistoryFlow.collectAsStateWithLifecycle()

    // clear navigation
//    LaunchedEffect(Unit) {
//        viewModel.onEvent(EventListScreenEvent.NavigateToRoute(null))
//    }

    ExampleContent(
        viewModel.state,
        {},
        navigate
    )
}

@Composable
private fun ExampleContent(
    state: EventHistoryScreenState,
    onEvent: (EventHistoryScreenEvent) -> Unit,
    navigate: NavigateToRoute
) {
    var nav_var by remember {
        mutableStateOf(false)
    }
    if (nav_var) {
        navigate(NavigationRoute.Event)
    }
    LazyColumn {
        items(state.eventHistoryList) {
            Row {
                Text(it.name)
                Text(it.time_created)
                Text(it.time_ended)
                Spacer(modifier = Modifier.weight(1f))
//                Button(
//                    onClick = { onEvent(EventHistoryScreenEvent.StartTracking(it.id)) }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Done,
//                        contentDescription = "Track"
//                    )
//                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun ExampleContentPreview() {
//    OOPProjektsTheme {
//        val state = EventHistoryScreenState()
//        state.eventHistoryList.addAll(listOf(
//            EventItem(0, "Item 1", "#000000", true),
//            EventItem(1, "Random","#000000", false),
//            EventItem(2, "abbdb","#000000", true),
//        ))
//        ExampleContent(state = state, onEvent = { }, navigate = { })
//    }
//}
