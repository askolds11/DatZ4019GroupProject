package com.grupacetri.oopprojekts.featureEvent.ui.create

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute
//import com.grupacetri.oopprojekts.featureFoo.ui.ExampleContent
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent
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
//    viewModel.fooListFlow.collectAsStateWithLifecycle()

    // clear navigation
//    LaunchedEffect(Unit) {
//        viewModel.onEvent(FooScreenEvent.NavigateToRoute(null))
//    }

//    ExampleContent(
//        viewModel.state,
//        viewModel::onEvent,
//        navigate
//    )
    Text(text = "abc")
}