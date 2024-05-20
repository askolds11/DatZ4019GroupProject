package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.lifecycle.ViewModel
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent
import me.tatarka.inject.annotations.Inject


@Inject
//@FooScope
class EventTimeInstanceFormViewModel (
    private val eventUseCases: EventUseCases
) : SideEffectViewModel<EventTimeInstanceFormEvent.SideEffectEvent>() {
    val state = EventTimeInstanceFormScreenState()
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
    fun onEvent(event: EventTimeInstanceFormEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventTimeInstanceFormEvent.UpdateTimeStarted -> updateTimeStarted(event.newValue)
            is EventTimeInstanceFormEvent.UpdateTimeEnded -> updateTimeEnded(event.newValue)
            is EventTimeInstanceFormEvent.Save -> save()
            EventTimeInstanceFormEvent.SideEffectEvent.NavigateToScreen999 -> TODO()
        }
    }

    private fun updateTimeStarted(color: String) {
//        state.eventFormItem.value = state.eventFormItem.value.copy(color = color)
    }

    private fun updateTimeEnded(comment: String) {
//        state.eventFormItem.value = state.eventFormItem.value.copy(comment = comment)
    }

    private fun save() {
//        eventUseCases.insert(state.eventFormItem.value)
    }
}