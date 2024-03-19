package com.grupacetri.oopprojekts.featureExample.domain

import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExampleUseCases(
    private val exampleRepository: ExampleRepository
) {
    fun getList(): Flow<List<ExampleItem>> {
        return exampleRepository.getList().map {
            it.mapIndexed { index, example ->
                ExampleItem(
                    example.id,
                    "${example.custom_string} $index"
                )
            }
        }
    }

    fun delete(id: Long) {
        exampleRepository.delete(id)
    }

    fun add(customString: String) {
        exampleRepository.add(customString)
    }

}