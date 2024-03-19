package com.grupacetri.oopprojekts.featureExample.data

class ExampleRepositoryImpl: ExampleRepository {
    private val listt = listOf("uno", "dos", "tres")
    override fun getList(): List<String> {
        return listt
    }

    override fun get(id: Int): String {
        return listt[id - 1]
    }
}