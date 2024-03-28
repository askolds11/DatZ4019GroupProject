package com.grupacetri.oopprojekts.featureSecondScreen.ui

// exhaustive list of all interactive events that can happen in SecondScreen
// these get sent to SecondViewModel.
sealed class SecondScreenEvent {
    data class UpdateText(val newValue: String): SecondScreenEvent()
}