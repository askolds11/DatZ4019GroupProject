package com.grupacetri.oopprojekts.featureExample.data

import com.grupacetri.oopprojekts.Example
import kotlinx.coroutines.flow.Flow

// interface for ExampleRepository
// this is an interface so we can easily replace it with e.g. fake data for testing purposes
interface ExampleRepository {
    fun getList(): Flow<List<Example>>
    fun delete(id: Long)
    fun add(customString: String)
}