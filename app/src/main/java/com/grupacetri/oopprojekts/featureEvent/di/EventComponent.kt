package com.grupacetri.oopprojekts.featureEvent.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.featureEvent.ui.create.EventFormScreen
import com.grupacetri.oopprojekts.featureFoo.data.FooRepository
import com.grupacetri.oopprojekts.featureFoo.data.FooRepositoryImpl
import com.grupacetri.oopprojekts.featureFoo.domain.FooUseCases
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreen
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
//    @FooScope
//    @Provides
//    fun provideFooRepository(database: Database): FooRepository = FooRepositoryImpl(database)
//
//    @FooScope
//    @Provides
//    fun provideFooUseCases(fooRepository: FooRepository): FooUseCases = FooUseCases(fooRepository)

    abstract val eventFormScreen: EventFormScreen
}