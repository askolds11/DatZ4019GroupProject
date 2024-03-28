package com.grupacetri.oopprojekts.core.di

import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DomainScope

@Component
@DomainScope
abstract class DomainComponent(@Component val dataComponent: DataComponent) {

    // this shows how to get an instance of exampleUseCases
    @DomainScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}