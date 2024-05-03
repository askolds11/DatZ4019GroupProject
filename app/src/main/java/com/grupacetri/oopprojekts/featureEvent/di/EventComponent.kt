package com.grupacetri.oopprojekts.featureEvent.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.featureEvent.ui.create.EventFormScreen
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventRepositoryImpl
//import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases //need domain
//import com.grupacetri.oopprojekts.featureEvent.ui.EventFormScreen //unresolved ref whatever that means
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
//    @EventScope //finish implementing di please
//    @Provides
//    fun provideEventRepository(database: Database): EventRepository = EventRepositoryImpl(database)
//
//    @EventScope
//    @Provides
//    fun provideEventUseCases(EventRepository: EventRepository): EventUseCases = EventUseCases(EventRepository)

    abstract val eventFormScreen: EventFormScreen
}