package com.grupacetri.oopprojekts.featureExample.domain

import com.grupacetri.oopprojekts.featureExample.data.ExampleRepository

class ExampleUseCases(
    private val exampleRepository: ExampleRepository
) {
    fun getList(): List<String> {
        return exampleRepository.getList().mapIndexed { index, example ->
            "$example $index"
        }
    }

    fun get(id: Int): String {
        return exampleRepository.get(id) + " ooga booga"
    }

}