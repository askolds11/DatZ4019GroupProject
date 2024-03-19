package com.grupacetri.oopprojekts.core.di

import android.app.Application
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import com.grupacetri.oopprojekts.featureExample.data.ExampleRepositoryImpl
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@SingletonScope
abstract class DatabaseComponent(
    private val applicationContext: Context
) {
    @SingletonScope
    @Provides
    fun provideDatabaseDriver(): SqlDriver = AndroidSqliteDriver(Database.Schema, applicationContext, "database.db")

    @SingletonScope
    @Provides
    fun provideDatabase(sqlDriver: SqlDriver): Database = Database(sqlDriver)

    @SingletonScope
    @Provides
    fun provideExampleUseCases(exampleRepository: ExampleRepository): ExampleUseCases = ExampleUseCases(exampleRepository)
}