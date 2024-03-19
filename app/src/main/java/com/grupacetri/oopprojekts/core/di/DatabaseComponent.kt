package com.grupacetri.oopprojekts.core.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@SingletonScope
abstract class DatabaseComponent {
    @SingletonScope
    @Provides
    fun provideDatabaseDriver(): SqlDriver = AndroidSqliteDriver(Database.Schema, context, "test.db")

    @SingletonScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}