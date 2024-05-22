package com.grupacetri.oopprojekts.featureSettings.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val database: Database
): SettingsRepository {

    override fun insert(setting: Settings) {
        database.settingsQueries.insert(setting.key, setting.category, setting.value_)
    }

    override fun update(setting: Settings) {
        database.settingsQueries.update(setting.value_, setting.key)
    }

    override fun insertOrUpdate(setting: Settings) {
        database.settingsQueries.insertOrUpdate(setting.key, setting.category, setting.value_)
    }

    override fun select(key: SettingsKey, fast: Boolean): Flow<Settings?> {
        val queryFlow = database.settingsQueries.selectByKey(key).asFlow()
        return if (fast) {
            queryFlow.mapToOneOrNull(Dispatchers.Main)
        } else {
            queryFlow.mapToOneOrNull(Dispatchers.IO)
        }
    }

    override fun selectByCategory(category: SettingsCategory): Flow<List<Settings>> {
        return database.settingsQueries.selectByCategory(category).asFlow().map {
            it.executeAsList()
        }
    }

    override fun delete(key: SettingsKey) {
        database.settingsQueries.delete(key)
    }
}