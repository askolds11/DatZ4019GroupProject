package com.grupacetri.oopprojekts.featureSettings.ui.mainScreen

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings

sealed class SettingsScreenEvent {
    data class SetSettingValue (val setting: AllSettings): SettingsScreenEvent()
    sealed class SideEffectEvent: BaseSideEffectEvent, SettingsScreenEvent() {
        data object NavigateToScreen999: SideEffectEvent()
    }
}