package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureFoo.ui.FooScreenEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
class EventTimeInstanceFormViewModel (
    private val eventUseCases: EventUseCases,
    @Assisted savedStateHandle: SavedStateHandle
) : SideEffectViewModel<EventTimeInstanceFormEvent.SideEffectEvent>() {
    val state = EventTimeInstanceFormScreenState()
//
    private val historyFormParams = savedStateHandle.toRoute<HistoryNavigationRoute.EventTimeInstanceForm>()

    init {
        viewModelScope.launch {
            loadData()
        }
    }
    private suspend fun loadData() {
        val eventTimeInstanceItem = eventUseCases.selectById(historyFormParams.id).first()
        with(eventTimeInstanceItem) {
            state.eventFormItem.value = state.eventFormItem.value.copy(
                time_created = time_created,
                time_ended = time_ended
        )

        }
    }
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

    private fun updateTimeStarted(time_created: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(time_created = time_created)
    }

    private fun updateTimeEnded(time_ended: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(time_ended = time_ended)
    }

    private fun save() {
//        eventUseCases.insert(state.eventFormItem.value)
    }
}