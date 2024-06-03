package com.grupacetri.oopprojekts.featureSettings.domain

import com.grupacetri.oopprojekts.core.ui.UiState
import com.grupacetri.oopprojekts.featureSettings.di.SettingsScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject

/**
 * Class with app settings. Accessing them is blocking.
 * When possible use [SettingsUseCases.getSetting].
 */
@Inject
@SettingsScope
class AppSettings(
    private val settingsUseCases: SettingsUseCases,
    private val coroutineScope: CoroutineScope
) {
    // individual settings
    val theme by lazy { getSetting(AllSettings.Theme()) }
    val language by lazy { getSetting(AllSettings.Language()) }

    /**
     * Get setting from database
     * @param default Default value of setting
     */
    private fun<T: AllSettings> getSetting(
        default: T
    ): StateFlow<UiState<T>> {
        return settingsUseCases.getSetting(default, true)
            .map {
                UiState.Success(it)
            }
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5000),
                UiState.Loading()
            )
    }
}