package com.grupacetri.oopprojekts.featureEvent.ui.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.navigation.NavigationRoute
import com.grupacetri.oopprojekts.featureFoo.di.FooScope
import com.grupacetri.oopprojekts.featureFoo.domain.FooUseCases
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject


@Inject
//@FooScope
class EventFormViewModel (
//    private val fooUseCases: FooUseCases
) : ViewModel() {
    val state = EventFormScreenState()
//
//    init {
//        Log.d("Test", "${fooUseCases.hashCode()}")
//    }
//
//    val fooListFlow: SharedFlow<Unit> = fooUseCases.getList()
//        .map {
//            state.fooList.clear()
//            state.fooList.addAll(it)
//            return@map //Unit
//        }.shareIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000)
//        )
//
//    // all screen events get handled here
    fun onEvent(event: EventFormScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventFormScreenEvent.UpdateColor -> updateColor(event.newValue)
            is EventFormScreenEvent.UpdateComment -> updateComment(event.newValue)
            is EventFormScreenEvent.UpdateName -> updateName(event.newValue)
        }
    }

    private fun updateColor(color: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(color = color)
    }

    private fun updateComment(comment: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(comment = comment)
    }

    private fun updateName(name: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(name = name)
    }
//
//    private fun delete(foo: Long?) {
//        if (foo != null) {
//            fooUseCases.delete(foo)
//        }
//    }
//
//    private fun save() {
//        val test = try {
//            state.inputText.value.toLong()
//        } catch (ex: NumberFormatException) {
//            0
//        }
//        // insert in database
//        fooUseCases.add(test)
//    }
//
//    private fun updateText(newValue: String) {
//        try {
//            newValue.toLong()
//            state.inputText.value = newValue
//        } catch (_: NumberFormatException) {
//        }
//    }
//
//    private fun navigateToRoute(route: NavigationRoute?) {
//        state.route.value = route
//    }
}