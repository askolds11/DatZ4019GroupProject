package com.grupacetri.oopprojekts.featureExample.di

import androidx.compose.runtime.Composable
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.SingletonScope
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import com.grupacetri.oopprojekts.featureExample.ui.ExampleScreen
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@SingletonScope
annotation class ExampleScope

@ExampleScope
@Component
abstract class ExampleComponent(@Component val databaseComponent: DatabaseComponent) {
    // ui
    abstract val exampleScreen: ExampleScreen
    protected abstract val database: Database

    // provide
    @ExampleScope
    @Provides
    fun provideExampleRepository(database: Database): ExampleRepository = ExampleRepositoryImpl(database)

    @ExampleScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}