package com.grupacetri.oopprojekts.featureExample.di

import androidx.compose.runtime.Composable
import com.grupacetri.oopprojekts.core.di.SingletonScope
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import com.grupacetri.oopprojekts.featureExample.ui.ExampleScreen
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@SingletonScope
abstract class ExampleComponent {
    // ui
    abstract val exampleScreen: ExampleScreen

    // provide
    @SingletonScope
    @Provides
    fun provideExampleRepository(): ExampleRepository = ExampleRepositoryImpl()

    @SingletonScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}