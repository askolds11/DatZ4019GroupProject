package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.FooNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias EventHistoryScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun EventHistoryScreen(
    eventViewModel: () -> EventHistoryScreenViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { eventViewModel() }
    viewModel.eventHistoryFlow.collectAsStateWithLifecycle()

    SideEffectComposable(viewModel) {
        when(it) {
            is EventHistoryScreenEvent.SideEffectEvent.NavigateToScreen999 -> {
                navigate(FooNavigationRoute.Foo)
                it.id
            }
        }
    }

    EventHistoryScreenContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@Composable
private fun EventHistoryScreenContent(
    state: EventHistoryScreenState,
    onEvent: (EventHistoryScreenEvent) -> Unit,
) {

    LazyColumn {
        items(state.eventHistoryList) {
            Row (modifier = Modifier
                    .clickable { onEvent(EventHistoryScreenEvent.SideEffectEvent.NavigateToScreen999(it.id))}
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                ){
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
