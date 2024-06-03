package com.grupacetri.oopprojekts.featureHistory.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.featureHistory.data.EventTimeInstanceRepository
import com.grupacetri.oopprojekts.featureHistory.data.EventTimeInstanceRepositoryImpl
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryUseCases
import com.grupacetri.oopprojekts.featureHistory.ui.historyFormScreen.HistoryFormScreen
import com.grupacetri.oopprojekts.featureHistory.ui.historyListScreen.HistoryListScreen
import com.grupacetri.oopprojekts.featureSettings.di.SettingsComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class HistoryScope

@Component
@HistoryScope
abstract class HistoryComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val settingsComponent: SettingsComponent
) {
    @HistoryScope
    @Provides
    fun provideEventTimeInstanceRepository(database: Database): EventTimeInstanceRepository = EventTimeInstanceRepositoryImpl(database)

    @HistoryScope
    @Provides
    fun provideHistoryUseCases(eventTimeInstanceRepository: EventTimeInstanceRepository): HistoryUseCases = HistoryUseCases(eventTimeInstanceRepository)

    abstract val eventTimeInstanceRepository: EventTimeInstanceRepository

    abstract val historyListScreen: HistoryListScreen

    abstract val historyFormScreen: HistoryFormScreen
}