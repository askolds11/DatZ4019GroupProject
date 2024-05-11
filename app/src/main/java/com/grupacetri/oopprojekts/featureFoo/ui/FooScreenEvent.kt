package com.grupacetri.oopprojekts.featureFoo.ui

import com.grupacetri.oopprojekts.core.ui.sideeffect.BaseSideEffectEvent

sealed class FooScreenEvent {
    data class Delete(val foo: Long?): FooScreenEvent()
    data class UpdateText(val newValue: String): FooScreenEvent()
    data object Save: FooScreenEvent()

    sealed class SideEffectEvent: BaseSideEffectEvent, FooScreenEvent() {
        data object NavigateToScreen999: SideEffectEvent()
    }
}