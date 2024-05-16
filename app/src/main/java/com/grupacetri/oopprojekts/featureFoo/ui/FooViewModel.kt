package com.grupacetri.oopprojekts.featureFoo.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureFoo.domain.FooUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class FooViewModel(
    private val fooUseCases: FooUseCases,
    @Assisted savedStateHandle: SavedStateHandle
) : SideEffectViewModel<FooScreenEvent.SideEffectEvent>() {
    val state = FooScreenState()

    init {
        // Example of Dependency Injection scopes
        // FooUseCases is scoped to the component,
        // so all ViewModels gets the same instance each time - check the logcat!
        Log.d("Test", "${fooUseCases.hashCode()}")
    }

    /**
     * Flow that, when active, updates [FooScreenState.fooList] if there is a database update
     */
    val fooListFlow: SharedFlow<Unit> = fooUseCases.getList()
        .map {
            state.fooList.clear()
            state.fooList.addAll(it)
            return@map //Unit
        }.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )

    // all screen events get handled here
    fun onEvent(event: FooScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is FooScreenEvent.Delete -> delete(event.foo)
            is FooScreenEvent.Save -> save()
            is FooScreenEvent.UpdateText -> updateText(event.newValue)
            is FooScreenEvent.SideEffectEvent.NavigateToScreen999 -> emitSideEffect(event)
        }
    }

    private fun delete(foo: Long?) {
        if (foo != null) {
            fooUseCases.delete(foo)
        }
    }

    private fun save() {
        val test = try {
            state.inputText.value.toLong()
        } catch (ex: NumberFormatException) {
            0
        }
        // insert in database
        fooUseCases.add(test)
    }

    private fun updateText(newValue: String) {
        try {
            newValue.toLong()
            state.inputText.value = newValue
        } catch (_: NumberFormatException) {
        }
    }
}