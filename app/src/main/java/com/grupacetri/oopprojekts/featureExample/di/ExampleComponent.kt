package com.grupacetri.oopprojekts.featureExample.di

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.SingletonScope
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import com.grupacetri.oopprojekts.featureExample.ui.ExampleScreen
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

// for dependency injection

// so you can provide one single instance throughout the app. not fully sure if this works, but it's
// something along these lines
@SingletonScope
annotation class ExampleScope

// inject database
@ExampleScope
@Component
abstract class ExampleComponent(@Component val databaseComponent: DatabaseComponent) {
    // ui - this composable needs some dependencies, you can call it from here
    abstract val exampleScreen: ExampleScreen
    // comes from databaseComponent - is dependency injected
    protected abstract val database: Database

    // this shows how to get an instance of exampleRepository
    @ExampleScope
    @Provides
    fun provideExampleRepository(database: Database): ExampleRepository = ExampleRepositoryImpl(database)

    // this shows how to get an instance of exampleUseCases
    @ExampleScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}