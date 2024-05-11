package com.grupacetri.oopprojekts.core.ui.sideeffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun <T : BaseSideEffectEvent> SideEffectComposable(
    viewModel: SideEffectViewModel<T>,
    onSideEffect: (T) -> Unit
) {
    val sideEffect = viewModel.sideEffectFlow.collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(sideEffect.value) {
        val sideEffectValue = sideEffect.value

        if (sideEffectValue == null) {
            return@LaunchedEffect
        }

        onSideEffect(sideEffectValue)
        viewModel.resetSideEffect()
    }
}