package com.grupacetri.oopprojekts.featureExample.ui

// exhaustive list of all interactive events that can happen in ExampleScreen
// these get sent to ExampleViewModel.
// they can also contain data - e.g. data class Delete(id) -,
// however it can also be a button click that does not need to pass any data - e.g. data object Save
sealed class ExampleScreenEvent {
    data class Delete(val id: Long): ExampleScreenEvent()
    data class UpdateText(val newValue: String): ExampleScreenEvent()
    data object Save: ExampleScreenEvent()
}