package com.grupacetri.oopprojekts.featureFoo.domain

import com.grupacetri.oopprojekts.featureFoo.data.FooRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FooUseCases(
    private val fooRepository: FooRepository
) {
    fun getList(): Flow<List<FooItem>> {
        return fooRepository.getList().map {
            it.mapIndexed { _, foo ->
                foo.toFooItem()
            }
        }
    }

    fun delete(id: Long) {
        fooRepository.delete(id)
    }

    fun add(foo: Long?) {
        fooRepository.insert(foo)
    }

}