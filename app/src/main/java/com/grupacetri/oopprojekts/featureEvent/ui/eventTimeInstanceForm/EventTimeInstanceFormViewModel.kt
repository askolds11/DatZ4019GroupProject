package com.grupacetri.oopprojekts.featureEvent.ui.eventTimeInstanceForm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
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
        state.eventTimeInstanceFormItem.value = eventTimeInstanceItem
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
            is EventTimeInstanceFormEvent.SideEffectEvent -> emitSideEffect(event)
        }
    }

    private fun updateTimeStarted(timeStarted: String) {
        val validation = eventUseCases.validateEventTime(timeStarted)
        state.eventTimeInstanceFormItem.value = state.eventTimeInstanceFormItem.value.copy(timeStarted = timeStarted)
        state.timeStartedValidation.value = validation
        checkValidations()
    }

    private fun updateTimeEnded(timeEnded: String) {
        val validation = eventUseCases.validateEventTime(timeEnded)
        state.eventTimeInstanceFormItem.value = state.eventTimeInstanceFormItem.value.copy(timeEnded = timeEnded)
        state.timeEndedValidation.value = validation
        checkValidations()
    }

    private fun save() {
        if (!eventUseCases.validate(state.eventTimeInstanceFormItem.value)) {
            state.timeStartedValidation.value = eventUseCases.validateEventTime(state.eventTimeInstanceFormItem.value.timeStarted)
            state.timeEndedValidation.value = eventUseCases.validateEventTime(state.eventTimeInstanceFormItem.value.timeEnded)
            return
        }
        eventUseCases.updateTimeInstance(state.eventTimeInstanceFormItem.value)
        emitSideEffect(EventTimeInstanceFormEvent.SideEffectEvent.NavigateBack)
    }

    private fun checkValidations() {
        if (state.timeStartedValidation.value != null || state.timeEndedValidation.value != null) {
            state.saveEnabled.value = false
        } else {
            state.saveEnabled.value = true
        }
    }
}