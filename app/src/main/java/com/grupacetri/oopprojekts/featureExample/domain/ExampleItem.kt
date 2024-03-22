package com.grupacetri.oopprojekts.featureExample.domain

import com.grupacetri.oopprojekts.Example

// you generally don't want to use classes from the Data layer in the UI layer and for logic
// this is a class for the domain and ui layer, with mappers so you can easily convert to/from data layer
data class ExampleItem(
    val id: Long,
    val customString: String
) {
    fun toExample(): Example {
        return Example(id = id, customString.ifBlank { null })
    }
}

fun Example.toItem(): ExampleItem {
    return ExampleItem(id, custom_string ?: "")
}