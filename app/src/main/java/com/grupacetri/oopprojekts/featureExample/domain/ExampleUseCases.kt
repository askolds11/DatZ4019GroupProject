package com.grupacetri.oopprojekts.featureExample.domain

import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// business logic goes here
// exampleRepository is the data layer - you can save/read examples from there
class ExampleUseCases(
    private val exampleRepository: ExampleRepository
) {
    fun getList(): Flow<List<ExampleItem>> {
        // simple logic example - add list index of the item to the end of it's name
        return exampleRepository.getList().map {
            it.mapIndexed { index, example ->
                ExampleItem(
                    example.id,
                    "${example.custom_string} $index"
                )
            }
        }
    }

    // both of these functions basically pass the call to the data layer.
    // we do not want ui layer to be aware of the data layer - this basically passes it through

    fun delete(id: Long) {
        exampleRepository.delete(id)
    }

    fun add(customString: String) {
        exampleRepository.add(customString)
    }

}