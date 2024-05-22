package com.grupacetri.oopprojekts.featureSettings.ui.mainScreen

import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import com.grupacetri.oopprojekts.featureSettings.domain.SettingsUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
class SettingsViewModel(
    private val settingsUseCases: SettingsUseCases
) : SideEffectViewModel<SettingsScreenEvent.SideEffectEvent>() {
    val state = SettingsScreenState()

    private val themeFlow = getSetting(AllSettings.Theme(), false) {
        state.theme.value = it
    }

    private val languageFlow = getSetting(AllSettings.Language(), false) {
        state.language.value = it
    }

    val settingsFlow: SharedFlow<Unit> = merge(themeFlow, languageFlow)
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )



    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SideEffectEvent -> emitSideEffect(event)
            is SettingsScreenEvent.SetSettingValue -> setSetting(event.setting)
        }
    }

    private fun setSetting(setting: AllSettings) {
        settingsUseCases.setSetting(setting)
    }

    /**
     * @param default Default value of setting
     * @param fast If this setting should be fetched using the main thread
     * @transform What transformation to apply to the settingValue
     */
    private inline fun<reified T: AllSettings> getSetting(
        default: T,
        fast: Boolean = false,
        crossinline transform: (setting: T) -> Unit
    ): Flow<Unit> {
        return settingsUseCases.getSetting(default, fast)
            .map {
                transform(it)
            }
    }
}