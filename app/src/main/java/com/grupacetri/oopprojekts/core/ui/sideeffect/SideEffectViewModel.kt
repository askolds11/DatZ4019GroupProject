package com.grupacetri.oopprojekts.core.ui.sideeffect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

/**
 * Interface for an event that is meant to be a SideEffect.
 */
interface BaseSideEffectEvent

/**
 * Base ViewModel that includes a flow for SideEffects and processing logic for it.
 */
abstract class SideEffectViewModel<T: BaseSideEffectEvent>: ViewModel() {
    private val _sideEffectFlow: MutableSharedFlow<T?> = MutableSharedFlow()
    /**
     * Flow that emits SideEffects
     */
    val sideEffectFlow = _sideEffectFlow
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )

    /**
     * Emit SideEffect to the SideEffect flow
     */
    protected fun emitSideEffect(effect: T) {
        viewModelScope.launch {
            _sideEffectFlow.emit(effect)
        }
    }

    /**
     * Emit null to the SideEffect flow
     */
    fun resetSideEffect() {
        viewModelScope.launch {
            _sideEffectFlow.emit(null)
        }
    }
}