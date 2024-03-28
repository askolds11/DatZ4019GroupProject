package com.grupacetri.oopprojekts.core.di

import com.grupacetri.oopprojekts.featureExample.ui.ExampleScreen
import com.grupacetri.oopprojekts.featureSecondScreen.ui.SecondScreen
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class UIScope

@UIScope
@Component
abstract class UIComponent(@Component protected val domainComponent: DomainComponent) {
    abstract val exampleScreen: ExampleScreen
    abstract val secondScreen: SecondScreen
}