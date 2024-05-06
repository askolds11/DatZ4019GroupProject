package com.grupacetri.oopprojekts.featureEvent.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.featureEvent.ui.form.EventFormScreen
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventRepositoryImpl
import com.grupacetri.oopprojekts.featureEvent.data.EventTimeInstanceRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventTimeInstanceRepositoryImpl
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureEvent.ui.history.EventHistoryScreen
import com.grupacetri.oopprojekts.featureEvent.ui.list.EventListScreen
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class EventScope

@Component
@EventScope
abstract class EventComponent(
    @Component val databaseComponent: DatabaseComponent
) {
    @EventScope
    @Provides
    fun provideEventRepository(database: Database): EventRepository = EventRepositoryImpl(database)

    @EventScope
    @Provides
    fun provideEventTimeInstanceRepository(database: Database): EventTimeInstanceRepository = EventTimeInstanceRepositoryImpl(database)

    @EventScope
    @Provides
    fun provideEventUseCases(eventRepository: EventRepository, eventTimeInstanceRepository: EventTimeInstanceRepository): EventUseCases = EventUseCases(eventRepository, eventTimeInstanceRepository)

    abstract val eventFormScreen: EventFormScreen

    abstract val eventListScreen: EventListScreen

    abstract val eventHistoryScreen: EventHistoryScreen
}