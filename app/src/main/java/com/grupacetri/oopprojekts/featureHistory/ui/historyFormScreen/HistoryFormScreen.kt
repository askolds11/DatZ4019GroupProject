package com.grupacetri.oopprojekts.featureHistory.ui.historyFormScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.core.ui.DarkLightPreviews
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryUseCases
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias HistoryFormScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun HistoryFormScreen(
    historyFormScreenViewModel: (SavedStateHandle) -> HistoryFormScreenViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { historyFormScreenViewModel(createSavedStateHandle()) }

    SideEffectComposable(viewModel) {
        when(it) {
            is HistoryFormScreenEvent.SideEffectEvent.NavigateBack -> {
                navigate(NavigationRoute.NavigateUp)
            }
        }
    }

    HistoryFormContent(
        viewModel.state,
        viewModel::onEvent
    )
}

@Composable
private fun HistoryFormContent(
    state: HistoryFormScreenState,
    onEvent: (HistoryFormScreenEvent) -> Unit
) {
    Column{
        HistoryFormTextField(
            label = stringResource(R.string.start_time),
            error = when(state.timeStartedValidation.value) {
                HistoryUseCases.EventTimeError.IS_EMPTY -> stringResource(
                    R.string.validation_error_empty,
                    stringResource(R.string.start_time)
                )
                HistoryUseCases.EventTimeError.NOT_DATETIME -> stringResource(
                    R.string.invalid_datetime,
                    stringResource(R.string.end_time)
                )
                null -> null
            },
            value = state.historyFormItem.value.timeStarted,
            onValueChange = {
                onEvent(HistoryFormScreenEvent.UpdateTimeStarted(it))
            },
        )
        Spacer(modifier = Modifier.height(10.dp))
        HistoryFormTextField(
            label = stringResource(R.string.end_time),
            error = when(state.timeEndedValidation.value) {
                HistoryUseCases.EventTimeError.IS_EMPTY -> stringResource(
                    R.string.validation_error_empty,
                    stringResource(R.string.end_time)
                )
                HistoryUseCases.EventTimeError.NOT_DATETIME -> stringResource(
                    R.string.invalid_datetime,
                    stringResource(R.string.end_time)
                )
                null -> null
            },
            value = state.historyFormItem.value.timeEnded?: "",
            onValueChange = {
                onEvent(HistoryFormScreenEvent.UpdateTimeEnded(it))
            }
        )

        Spacer(modifier = Modifier.weight(2f))
        IconButton(
            onClick = { onEvent(HistoryFormScreenEvent.Delete) },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.errorContainer)

        ) {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                onEvent(HistoryFormScreenEvent.Save)
            },
            enabled = state.saveEnabled.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp)
                .height(100.dp)
        )
        {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
private fun HistoryFormTextField(
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
private fun HistoryContentPreview() {
    OOPProjektsTheme {
        val state = HistoryFormScreenState()
        HistoryFormContent(state = state, onEvent = {})
    }
}
