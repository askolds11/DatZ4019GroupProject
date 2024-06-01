package com.grupacetri.oopprojekts.featureEvent.ui.form

//import com.grupacetri.oopprojekts.featureFoo.ui.ExampleContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias EventFormScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun EventFormScreen(
    eventFormViewModel: (SavedStateHandle) -> EventFormViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { eventFormViewModel(createSavedStateHandle()) }

    SideEffectComposable(viewModel) {
        when(it) {
            is EventFormScreenEvent.SideEffectEvent.NavigateUp -> {
                navigate(NavigationRoute.NavigateUp)
            }
        }
    }

    EventFormScreenContent(
        viewModel.state,
        viewModel::onEvent,
        navigate
    )
}

@Composable
private fun EventFormScreenContent(
    state: EventFormScreenState,
    onEvent: (EventFormScreenEvent) -> Unit,
    navigate: NavigateToRoute2
) {
    Column{
        EventFormTextField(
            label = "Enter new name",
            error = when(state.nameValidation.value) {
                EventUseCases.EventNameError.IS_EMPTY -> "Name cannot be empty."
                EventUseCases.EventNameError.TOO_LONG -> "Name must be less than 100."
                null -> null
            },
            value = state.eventFormItem.value.name,
            onValueChange = {
                onEvent(EventFormScreenEvent.UpdateName(it))
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        EventFormTextField(
            label = "Comment",
            value = state.eventFormItem.value.comment ?: "",
            onValueChange = {
                onEvent(EventFormScreenEvent.UpdateComment(it))
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        EventFormTextField(
            label = "Color",
            error = when(state.colorValidation.value) {
                EventUseCases.EventColorError.IS_EMPTY -> "Color cannot be empty."
                EventUseCases.EventColorError.INVALID -> "Color is an invalid hexcode."
                null -> null
            },
            value = state.eventFormItem.value.color,
            onValueChange = {
                onEvent(EventFormScreenEvent.UpdateColor(it))
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        Row (verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.eventFormItem.value.active,
                onCheckedChange = {
                    onEvent(EventFormScreenEvent.UpdateActive(it))
                }
            )
            Text(text = "Active")
        }


        Spacer(modifier = Modifier.weight(2f))
        Button(
            onClick = {
                onEvent(EventFormScreenEvent.Save)
            },
            enabled = state.saveEnabled.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp)
                .height(100.dp)
        )
        {
            Text(text = "ADD NEW")
        }
    }
}

@Composable
private fun EventFormTextField(
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
        val state = EventFormScreenState()
        EventFormScreenContent(state = state, onEvent = {}, navigate = {})

    }
}
