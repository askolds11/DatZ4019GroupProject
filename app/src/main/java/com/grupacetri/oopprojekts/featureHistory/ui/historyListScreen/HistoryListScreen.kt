package com.grupacetri.oopprojekts.featureHistory.ui.historyListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import java.util.Locale

typealias HistoryListScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun HistoryListScreen(
    eventViewModel: () -> HistoryListScreenViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { eventViewModel() }
    viewModel.eventHistoryFlow.collectAsStateWithLifecycle()

    SideEffectComposable(viewModel) {
        when (it) {
            is HistoryListScreenEvent.SideEffectEvent.NavigateToForm -> {
                navigate(HistoryNavigationRoute.HistoryForm(it.id))
            }
        }
    }

    HistoryListContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@Composable
private fun HistoryListContent(
    state: HistoryListScreenState,
    onEvent: (HistoryListScreenEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { onEvent(HistoryListScreenEvent.PreviousDay) }) {
                    Text("-")
                }
                Text(state.date.value.date.toString())
                Button(onClick = { onEvent(HistoryListScreenEvent.NextDay) }) {
                    Text("+")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        items(state.eventHistoryList) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        onEvent(
                            HistoryListScreenEvent.SideEffectEvent.NavigateToForm(
                                event.id
                            )
                        )
                    },
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = event.name,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${stringResource(R.string.start_time)}: ${event.timeCreated}"
                        )
                        Text(
                            text = "${stringResource(R.string.end_time)}: ${event.timeEnded}"
                        )
                        Text(
                            text = "${stringResource(R.string.duration)}: ${event.diff} ${
                                stringResource(
                                    when (state.timeDiffFormat.value.format) {
                                        AllSettings.TimeDiffFormat.TimeDiffFormatValue.Minutes -> R.string.minutes
                                        AllSettings.TimeDiffFormat.TimeDiffFormatValue.Seconds -> R.string.seconds
                                    }
                                ).lowercase(Locale.getDefault())
                            }"
                        )
                    }
                }
            }
        }
    }
}
