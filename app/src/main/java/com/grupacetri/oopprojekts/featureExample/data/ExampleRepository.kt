package com.grupacetri.oopprojekts.featureExample.data

interface ExampleRepository {
    fun getList(): List<String>
    fun get(id: Int): String
}