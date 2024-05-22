package com.grupacetri.oopprojekts.core.ui

sealed interface UiState<out T> {
    class Loading<out T>: UiState<T>
    data class Success<out T>(val data: T): UiState<T>
}