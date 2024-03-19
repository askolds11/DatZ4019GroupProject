package com.grupacetri.oopprojekts.featureExample.domain

import com.grupacetri.oopprojekts.Example

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