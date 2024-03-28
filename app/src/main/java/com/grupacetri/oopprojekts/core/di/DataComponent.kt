package com.grupacetri.oopprojekts.core.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DataScope

@DataScope
@Component
abstract class DataComponent(@Component val databaseComponent: DatabaseComponent) {
    // this shows how to get an instance of exampleRepository
    @DataScope
    @Provides
    fun provideExampleRepository(database: Database): ExampleRepository = ExampleRepositoryImpl(database)
}