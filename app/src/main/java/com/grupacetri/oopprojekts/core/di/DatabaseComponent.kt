package com.grupacetri.oopprojekts.core.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.grupacetri.oopprojekts.Database
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

// setup database stuff for app runtime
@Component
@SingletonScope
abstract class DatabaseComponent(
    private val applicationContext: Context
) {
    @SingletonScope
    @Provides
    fun provideDatabaseDriver(): SqlDriver = AndroidSqliteDriver(Database.Schema, applicationContext, "database.db")

    // one instance of the database for whole app
    @SingletonScope
    @Provides
    fun provideDatabase(sqlDriver: SqlDriver): Database = Database(sqlDriver)
}