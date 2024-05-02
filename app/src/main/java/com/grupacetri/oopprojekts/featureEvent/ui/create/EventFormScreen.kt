package com.grupacetri.oopprojekts.featureEvent.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureFoo.domain.FooItem
//import com.grupacetri.oopprojekts.featureFoo.ui.ExampleContent
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenState
import com.grupacetri.oopprojekts.featureFoo.ui.FooViewModel
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias EventFormScreen = @Composable (navigate: NavigateToRoute) -> Unit

@Inject
@Composable
fun EventFormScreen(
    eventFormViewModel: () -> EventFormViewModel,
    @Assisted navigate: NavigateToRoute
) {
    val viewModel = viewModel { eventFormViewModel() }
//    viewModel.eventListFlow.collectAsStateWithLifecycle()

    // clear navigation
//    LaunchedEffect(Unit) {
//        viewModel.onEvent(FooScreenEvent.NavigateToRoute(null))
//    }

//    ExampleContent(
//        viewModel.state,
//        viewModel::onEvent,
//        navigate
//    )
    Text(text = "abccccccccc")
}

@Composable
private fun EventFormExampleContent(
    state: EventFormScreenState,
    onEvent: (EventFormScreenEvent) -> Unit,
    navigate: NavigateToRoute
) {
    LazyColumn {

    }
}


@Preview
@Composable
private fun EventFormExampleContentPreview() {
    OOPProjektsTheme {
        val state = EventFormScreenState()
        EventFormExampleContent(state = state, onEvent = { }, navigate = {})
        Column {
            Text(text = "Enter new name")
            OutlinedTextField(value = "Name", onValueChange = {} )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Comment")
            OutlinedTextField(value = "Comment", onValueChange = {} )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = "Color Dropwdown", onValueChange = {} )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = "Category Dropwdown", onValueChange = {} )
//            Spacer(modifier = Modifier.height(10.dp))
//            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(100.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "TRACK")
            }
        }

    }
}
