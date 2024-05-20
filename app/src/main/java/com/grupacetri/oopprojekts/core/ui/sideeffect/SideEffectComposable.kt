package com.grupacetri.oopprojekts.core.ui.sideeffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Composable that launches side effects from [SideEffectViewModel]
 * @param viewModel ViewModel that inherits from [SideEffectViewModel]
 * @param onSideEffect Function that gets executed when a SideEffect is emitted.
 * Typically a when block with an exhaustive list of [T]
 */
@Composable
fun <T : BaseSideEffectEvent> SideEffectComposable(
    viewModel: SideEffectViewModel<T>,
    onSideEffect: (T) -> Unit
) {
    // Collect the SideEffect flow
    val sideEffect by viewModel.sideEffectFlow
        .collectAsStateWithLifecycle(null)


    LaunchedEffect(sideEffect) {
        val sideEffectValue = sideEffect

        // If SideEffect is null, then there is no SideEffect
        if (sideEffectValue == null) {
            return@LaunchedEffect
        }

        // Process side effect
        onSideEffect(sideEffectValue)

        // Reset side effect, so if you emit the same side effect,
        // the state changes and the LaunchedEffect is run again
        viewModel.resetSideEffect()
    }
}