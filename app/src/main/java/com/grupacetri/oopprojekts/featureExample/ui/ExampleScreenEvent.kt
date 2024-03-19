package com.grupacetri.oopprojekts.featureExample.ui

sealed class ExampleScreenEvent {
    data class Delete(val id: Long): ExampleScreenEvent()
    data class UpdateText(val newValue: String): ExampleScreenEvent()
    data object Save: ExampleScreenEvent()
}