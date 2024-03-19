package com.grupacetri.oopprojekts.featureExample.data

import com.grupacetri.oopprojekts.Example
import kotlinx.coroutines.flow.Flow

interface ExampleRepository {
    fun getList(): Flow<List<Example>>
    fun delete(id: Long)
    fun add(customString: String)
}