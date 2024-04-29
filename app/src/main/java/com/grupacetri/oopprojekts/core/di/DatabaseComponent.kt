package com.grupacetri.oopprojekts.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.grupacetri.oopprojekts.Database
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DatabaseScope

@Component
@DatabaseScope
abstract class DatabaseComponent(
    private val applicationContext: Context
) {
    @DatabaseScope
    @Provides
    fun provideDatabaseDriver(): SqlDriver = AndroidSqliteDriver(Database.Schema, applicationContext, "database.db")

    // one instance of the database for whole app
    @DatabaseScope
    @Provides
    fun provideDatabase(sqlDriver: SqlDriver): Database = Database(sqlDriver)
}