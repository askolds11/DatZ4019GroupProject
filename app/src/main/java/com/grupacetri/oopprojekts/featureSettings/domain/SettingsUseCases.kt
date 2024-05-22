package com.grupacetri.oopprojekts.featureSettings.domain

import com.grupacetri.oopprojekts.featureSettings.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsUseCases(
    private val settingsRepository: SettingsRepository
) {
    /**
     * @param default Default value of setting
     * @param fast Whether to retrieve value on Main thread.
     */
    fun <T: AllSettings> getSetting(default: T, fast: Boolean = false): Flow<T> {
        val key = getKey(default)
        return settingsRepository.select(key, fast).map {
            it?.toAllSettings() ?: default
        }
    }


    fun setSetting(setting: AllSettings) {
        settingsRepository.insertOrUpdate(
            setting.toSettings()
        )
    }

//    fun delete(key: SettingsKey) {
//        settingsRepository.delete(key)
//    }

}