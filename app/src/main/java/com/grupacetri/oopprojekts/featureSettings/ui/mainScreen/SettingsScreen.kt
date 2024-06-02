package com.grupacetri.oopprojekts.featureSettings.ui.mainScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.R
import com.grupacetri.oopprojekts.core.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.core.ui.CustomAlertDialog
import com.grupacetri.oopprojekts.core.ui.navigation.NavigateToRoute2
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectComposable
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

typealias SettingsScreen = @Composable (navigate: NavigateToRoute2) -> Unit

@Inject
@Composable
fun SettingsScreen(
    settingsViewModel: () -> SettingsViewModel,
    @Assisted navigate: NavigateToRoute2
) {
    val viewModel = viewModel { settingsViewModel() }
    viewModel.settingsFlow.collectAsStateWithLifecycle()

    SideEffectComposable(viewModel) {
    }

    SettingsContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun SettingsContent(
    state: SettingsScreenState,
    onEvent: (SettingsScreenEvent) -> Unit
) {
    LazyColumn {
        item {
            ThemeSetting(
                state.theme.value,
                onClick = { onEvent(SettingsScreenEvent.SetSettingValue(it)) }
            )
        }
        item {
            LanguageSetting(
                language = state.language.value,
                onClick = { onEvent(SettingsScreenEvent.SetSettingValue(it)) }
            )
        }
        item {
            TimeDiffSetting(
                timeDiff = state.timeDiffFormat.value,
                onClick = { onEvent(SettingsScreenEvent.SetSettingValue(it)) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeSetting(
    theme: AllSettings.Theme,
    onClick: (AllSettings.Theme) -> Unit
) {
    data class ThemeItem(
        val themeValue: AllSettings.Theme.ThemeValue,
        @StringRes val stringId: Int
    )
    val items = remember {
        listOf(
            ThemeItem(AllSettings.Theme.ThemeValue.DARK, R.string.dark),
            ThemeItem(AllSettings.Theme.ThemeValue.LIGHT, R.string.light),
            ThemeItem(AllSettings.Theme.ThemeValue.SYSTEM, R.string.system)
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.theme))
        Spacer(modifier = Modifier.weight(1f))
        SingleChoiceSegmentedButtonRow {
            items.forEachIndexed { index, it ->
                SegmentedButton(
                    selected = theme.theme == it.themeValue,
                    onClick = { onClick(AllSettings.Theme(it.themeValue)) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = items.count())
                ) {
                    Text(stringResource(it.stringId))
                }
            }
        }
    }
}

@Composable
private fun LanguageSetting(
    language: AllSettings.Language,
    onClick: (AllSettings.Language) -> Unit
) {
    var dialogOpen by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { dialogOpen = true }
    ) {
        Text(stringResource(R.string.language))
        Spacer(modifier = Modifier.weight(1f))
        Text(stringResource(language.language.uiString))
    }

    CustomAlertDialog(
        visible = dialogOpen,
        onDismissRequest = { dialogOpen = false },
        title = { Text(stringResource(R.string.choose_language)) }
    ) {
        val items = listOf(
            AllSettings.Language.LanguageValue.System,
            AllSettings.Language.LanguageValue.English,
            AllSettings.Language.LanguageValue.Latvian
        )
        LazyColumn {
            items(items) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(AllSettings.Language(it)); dialogOpen = false }
                ) {
                    RadioButton(
                        selected = language.language == it,
                        onClick = { onClick(AllSettings.Language(it)); dialogOpen = false },
                    )
                    Text(
                        text = stringResource(it.uiString),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeDiffSetting(
    timeDiff: AllSettings.TimeDiffFormat,
    onClick: (AllSettings.TimeDiffFormat) -> Unit
) {
    var dialogOpen by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { dialogOpen = true }
    ) {
        Text(stringResource(R.string.time_format))
        Spacer(modifier = Modifier.weight(1f))
        Text(stringResource(timeDiff.format.uiString))
    }

    CustomAlertDialog(
        visible = dialogOpen,
        onDismissRequest = { dialogOpen = false },
        title = { Text(stringResource(R.string.izv_lies_form_tu)) }
    ) {
        val items = listOf(
            AllSettings.TimeDiffFormat.TimeDiffFormatValue.Seconds,
            AllSettings.TimeDiffFormat.TimeDiffFormatValue.Minutes,
        )
        LazyColumn {
            items(items) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(AllSettings.TimeDiffFormat(it)); dialogOpen = false }
                ) {
                    RadioButton(
                        selected = timeDiff.format == it,
                        onClick = { onClick(AllSettings.TimeDiffFormat(it)); dialogOpen = false },
                    )
                    Text(
                        text = stringResource(it.uiString),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}