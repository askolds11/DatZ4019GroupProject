package com.grupacetri.oopprojekts.featureExample.di

import com.grupacetri.oopprojekts.core.di.SingletonScope
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@SingletonScope
abstract class ExampleComponent {
    @SingletonScope
    @Provides
    fun provideExample(): Example = Example()
}