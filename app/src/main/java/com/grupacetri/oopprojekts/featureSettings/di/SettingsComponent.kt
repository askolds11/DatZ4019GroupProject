package com.grupacetri.oopprojekts.featureSettings.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.featureSettings.data.SettingsRepository
import com.grupacetri.oopprojekts.featureSettings.data.SettingsRepositoryImpl
import com.grupacetri.oopprojekts.featureSettings.domain.AppSettings
import com.grupacetri.oopprojekts.featureSettings.domain.SettingsUseCases
import com.grupacetri.oopprojekts.featureSettings.ui.mainScreen.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class SettingsScope

@Component
@SettingsScope
abstract class SettingsComponent(
    @Component val databaseComponent: DatabaseComponent,
    private val coroutineScope: CoroutineScope
) {
    @SettingsScope
    @Provides
    fun provideSettingsRepository(database: Database): SettingsRepository = SettingsRepositoryImpl(database)

    @SettingsScope
    @Provides
    fun provideSettingsUseCases(settingsRepository: SettingsRepository): SettingsUseCases = SettingsUseCases(settingsRepository)

    @SettingsScope
    @Provides
    fun provideAppSettings(settingsUseCases: SettingsUseCases): AppSettings = AppSettings(settingsUseCases, coroutineScope)

    abstract val settings: AppSettings

    abstract val settingsScreen: SettingsScreen
}