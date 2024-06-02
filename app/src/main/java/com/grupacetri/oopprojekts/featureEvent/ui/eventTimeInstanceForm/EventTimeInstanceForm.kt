package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.ui.DarkLightPreviews
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
//import com.grupacetri.oopprojekts.featureFoo.ui.ExampleContent
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias EventTimeInstanceFormScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun EventTimeInstanceFormScreen(
    eventTimeInstanceFormViewModel: (SavedStateHandle) -> EventTimeInstanceFormViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { eventTimeInstanceFormViewModel(createSavedStateHandle()) }

    SideEffectComposable(viewModel) {
        when(it) {
            is EventTimeInstanceFormEvent.SideEffectEvent.NavigateBack -> {
                navigate(NavigationRoute.NavigateUp)
            }
        }
    }

    EventTimeInstanceFormScreenContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventTimeInstanceFormScreenContent(
    state: EventTimeInstanceFormScreenState,
    onEvent: (EventTimeInstanceFormEvent) -> Unit
) {
    Column{
        EventTimeInstanceFormTextField(
            label = "Start time",
            error = when(state.timeStartedValidation.value) {
                EventUseCases.EventTimeError.IS_EMPTY -> "Time cannot be empty."
                EventUseCases.EventTimeError.NOT_DATETIME -> "Datetime should match the datetime format."
                null -> null
            },
            value = state.eventTimeInstanceFormItem.value.timeStarted,
            onValueChange = {
                onEvent(EventTimeInstanceFormEvent.UpdateTimeStarted(it))
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        EventTimeInstanceFormTextField(
            label = "End time",
            error = when(state.timeEndedValidation.value) {
                EventUseCases.EventTimeError.IS_EMPTY -> "Time cannot be empty."
                EventUseCases.EventTimeError.NOT_DATETIME -> "Datetime should match the datetime format."
                null -> null
            },
            value = state.eventTimeInstanceFormItem.value.timeEnded?: "",
            onValueChange = {
                onEvent(EventTimeInstanceFormEvent.UpdateTimeEnded(it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                onEvent(EventTimeInstanceFormEvent.Save)
            },
            enabled = state.saveEnabled.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp)
                .height(100.dp)
        )
        {
            Text(text = "SAVE")
        }
    }
}

@Composable
private fun EventTimeInstanceFormTextField(
    label: String,
    error: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(text = label)},
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
         },
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 20.dp)
    )
}



@DarkLightPreviews
@Composable
private fun EventFormExampleContentPreview() {
    OOPProjektsTheme {
        val state = EventTimeInstanceFormScreenState()
        EventTimeInstanceFormScreenContent(state = state, onEvent = {})
    }
}
