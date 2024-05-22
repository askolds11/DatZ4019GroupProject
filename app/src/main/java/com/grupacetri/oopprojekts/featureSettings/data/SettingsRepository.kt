package com.grupacetri.oopprojekts.featureSettings.data

import com.grupacetri.oopprojekts.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun insert(setting: Settings)
    fun update(setting: Settings)
    fun insertOrUpdate(setting: Settings)
    fun select(key: SettingsKey, fast: Boolean = false): Flow<Settings?>
    fun selectByCategory(category: SettingsCategory): Flow<List<Settings>>
    fun delete(key: SettingsKey)
}