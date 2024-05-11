package com.grupacetri.oopprojekts.core.ui.sideeffect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

interface BaseSideEffectEvent

abstract class SideEffectViewModel<T: BaseSideEffectEvent>: ViewModel() {
    private val _sideEffectFlow: MutableSharedFlow<T?> = MutableSharedFlow()
    val sideEffectFlow = _sideEffectFlow
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )

    protected fun emitSideEffect(effect: T) {
        viewModelScope.launch {
            _sideEffectFlow.emit(effect)
        }
    }

    fun resetSideEffect() {
        viewModelScope.launch {
            _sideEffectFlow.emit(null)
        }
    }
}