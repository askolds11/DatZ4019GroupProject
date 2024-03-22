package com.grupacetri.oopprojekts.featureExample.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Example
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

// implementation for ExampleRepository
// this is for normal usage
// for testing you'd probably define a ExampleRepositoryTestImpl in the testing module.
class ExampleRepositoryImpl(
    private val database: Database
): ExampleRepository {
    override fun getList(): Flow<List<Example>> {
        return database.exampleQueries.selectList().asFlow().mapToList(Dispatchers.IO)
    }

    override fun delete(id: Long) {
        database.exampleQueries.delete(id)
    }

    override fun add(customString: String) {
        database.exampleQueries.insert(customString)
    }
}