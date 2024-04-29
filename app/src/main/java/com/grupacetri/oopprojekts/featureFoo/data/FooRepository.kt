package com.grupacetri.oopprojekts.featureFoo.data

import com.grupacetri.oopprojekts.Foo
import kotlinx.coroutines.flow.Flow

interface FooRepository {
    fun getList(): Flow<List<Foo>>
    fun delete(id: Long)
    fun insert(foo: Long?)
}