package com.grupacetri.oopprojekts.featureSettings.ui.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings

@Stable
class SettingsScreenState {
    val theme: MutableState<AllSettings.Theme> = mutableStateOf(AllSettings.Theme())
    val language: MutableState<AllSettings.Language> = mutableStateOf(AllSettings.Language())
}