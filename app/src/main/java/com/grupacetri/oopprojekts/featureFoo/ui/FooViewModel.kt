package com.grupacetri.oopprojekts.featureFoo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.featureFoo.di.FooScope
import com.grupacetri.oopprojekts.featureFoo.domain.FooUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
@FooScope
class FooViewModel(
    private val fooUseCases: FooUseCases
) : ViewModel() {
    val state = FooScreenState()

    init {
        Log.d("Test", "${fooUseCases.hashCode()}")
    }

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
            is FooScreenEvent.NavigateToRoute -> navigateToRoute(event.route)
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

    private fun navigateToRoute(route: NavigationRoute?) {
        state.route.value = route
    }
}