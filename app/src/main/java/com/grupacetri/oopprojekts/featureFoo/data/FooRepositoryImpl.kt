package com.grupacetri.oopprojekts.featureFoo.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Foo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FooRepositoryImpl(
    private val database: Database
): FooRepository {
    override fun getList(): Flow<List<Foo>> {
        return database.fooQueries.selectList().asFlow().mapToList(Dispatchers.IO)
    }

    override fun delete(id: Long) {
        database.fooQueries.delete(id)
    }

    override fun add(foo: Long?) {
        database.fooQueries.insert(foo)
    }
}