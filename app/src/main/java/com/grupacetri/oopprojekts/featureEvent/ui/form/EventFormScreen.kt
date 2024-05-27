package com.grupacetri.oopprojekts.featureEvent.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.core.ui.DarkLightPreviews
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureEvent.ui.list.EventListScreenEvent
//import com.grupacetri.oopprojekts.featureFoo.ui.ExampleContent
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
        Spacer(modifier = Modifier.height(10.dp))
        EventFormTextField(
            label = "Comment",
            value = state.eventFormItem.value.comment ?: "",
            onValueChange = {
                onEvent(EventFormScreenEvent.UpdateComment(it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        EventFormTextField(
            label = "Color",
            value = state.eventFormItem.value.color,
            onValueChange = {
                onEvent(EventFormScreenEvent.UpdateColor(it))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        EventFormTextField(
            label = "Category",
            value = "Category",
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                onEvent(EventFormScreenEvent.Save)
            },
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
