package com.grupacetri.oopprojekts.core.di

import me.tatarka.inject.annotations.Scope
import kotlin.annotation.AnnotationTarget.*

@Scope
@Target(CLASS, FUNCTION, PROPERTY_GETTER)
annotation class SingletonScope