package com.grupacetri.oopprojekts.featureHistory.ui.historyFormScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.grupacetri.oopprojekts.core.ui.navigation.HistoryNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryUseCases
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
class HistoryFormScreenViewModel (
    private val historyUseCases: HistoryUseCases,
    @Assisted savedStateHandle: SavedStateHandle
) : SideEffectViewModel<HistoryFormScreenEvent.SideEffectEvent>() {
    val state = HistoryFormScreenState()
//
    private val historyFormParams = savedStateHandle.toRoute<HistoryNavigationRoute.HistoryForm>()

    init {
        viewModelScope.launch {
            loadData()
        }
    }
    // load and fill state from db
    private suspend fun loadData() {
        val eventTimeInstanceItem = historyUseCases.selectById(historyFormParams.id).first()
        state.historyFormItem.value = eventTimeInstanceItem
    }

    fun onEvent(event: HistoryFormScreenEvent) {
        when (event) {
            is HistoryFormScreenEvent.UpdateTimeStarted -> updateTimeStarted(event.newValue)
            is HistoryFormScreenEvent.UpdateTimeEnded -> updateTimeEnded(event.newValue)
            is HistoryFormScreenEvent.Save -> save()
            is HistoryFormScreenEvent.SideEffectEvent -> emitSideEffect(event)
            is HistoryFormScreenEvent.Delete -> delete()
        }
    }

    private fun updateTimeStarted(timeStarted: String) {
        val validation = historyUseCases.validateHistoryTime(timeStarted)
        state.historyFormItem.value = state.historyFormItem.value.copy(timeStarted = timeStarted)
        state.timeStartedValidation.value = validation
        checkValidations()
    }

    private fun updateTimeEnded(timeEnded: String) {
        val validation = historyUseCases.validateHistoryTime(timeEnded)
        state.historyFormItem.value = state.historyFormItem.value.copy(timeEnded = timeEnded)
        state.timeEndedValidation.value = validation
        checkValidations()
    }

    private fun save() {
        // if validations do not pass, recheck validations and don't save or go back
        if (!historyUseCases.validate(state.historyFormItem.value)) {
            state.timeStartedValidation.value = historyUseCases.validateHistoryTime(state.historyFormItem.value.timeStarted)
            state.timeEndedValidation.value = historyUseCases.validateHistoryTime(state.historyFormItem.value.timeEnded)
            checkValidations()
            return
        }
        historyUseCases.updateTimeInstance(state.historyFormItem.value)
        emitSideEffect(HistoryFormScreenEvent.SideEffectEvent.NavigateBack)
    }

    // check if all validations are empty - if so set saveEnabled to true, otherwise false
    private fun checkValidations() {
        if (state.timeStartedValidation.value != null || state.timeEndedValidation.value != null) {
            state.saveEnabled.value = false
        } else {
            state.saveEnabled.value = true
        }
    }

    private fun delete() {
        historyUseCases.delete(historyFormParams.id)
        emitSideEffect(HistoryFormScreenEvent.SideEffectEvent.NavigateBack)
    }
}