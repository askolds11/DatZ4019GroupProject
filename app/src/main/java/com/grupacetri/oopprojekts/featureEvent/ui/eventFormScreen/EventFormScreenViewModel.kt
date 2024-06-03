package com.grupacetri.oopprojekts.featureEvent.ui.eventFormScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
class EventFormScreenViewModel (
    private val eventUseCases: EventUseCases,
    @Assisted savedStateHandle: SavedStateHandle,
) : SideEffectViewModel<EventFormScreenEvent.SideEffectEvent>() {
    val state = EventFormScreenState()

    private val eventParams = savedStateHandle.toRoute<EventNavigationRoute.Event>()
    init {
        viewModelScope.launch {
            loadData()
        }
    }

    // if mode is edit, load data from database
    private suspend fun loadData() {
        if (eventParams.id != 0L) {
            val eventItem = eventUseCases.getById(eventParams.id).first()
            state.eventFormItem.value = eventItem
        }
        state.isEditMode.value = (eventParams.id != 0L)
    }

    fun onEvent(event: EventFormScreenEvent) {
        when (event) {
            is EventFormScreenEvent.UpdateColor -> updateColor(event.newValue)
            is EventFormScreenEvent.UpdateComment -> updateComment(event.newValue)
            is EventFormScreenEvent.UpdateName -> updateName(event.newValue)
            is EventFormScreenEvent.UpdateActive -> updateActive(event.newValue)
            is EventFormScreenEvent.Save -> save()
            is EventFormScreenEvent.SideEffectEvent -> emitSideEffect(event)
           
        }
    }

    private fun updateName(name: String) {
        val validation = eventUseCases.validateEventName(name)
        state.eventFormItem.value = state.eventFormItem.value.copy(name = name)
        state.nameValidation.value = validation
        checkValidations()
    }

    private fun updateComment(comment: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(comment = comment)
        if (eventParams.id != 0L) {
            checkValidations()
        }
    }

    private fun updateColor(color: String) {
        val validation = eventUseCases.validateEventColor(color)
        state.eventFormItem.value = state.eventFormItem.value.copy(color = color)
        state.colorValidation.value = validation
        checkValidations()
    }

    private fun updateActive(active: Boolean) {
        state.eventFormItem.value = state.eventFormItem.value.copy(active = active)
        if (eventParams.id != 0L) {
            checkValidations()
        }
    }

    // check if all validations are empty - if so set saveEnabled to true, otherwise false
    private fun checkValidations() {
        if (state.nameValidation.value != null || state.colorValidation.value != null) {
            state.saveEnabled.value = false
        } else {
            state.saveEnabled.value = true
        }
    }

    private fun save() {
        // if validations do not pass, recheck validations and don't save or go back
        if (!eventUseCases.validate(state.eventFormItem.value)) {
            state.nameValidation.value = eventUseCases.validateEventName(state.eventFormItem.value.name)
            state.colorValidation.value = eventUseCases.validateEventColor(state.eventFormItem.value.color)
            checkValidations()
            return
        }
        // if edit, update
        if (eventParams.id != 0L) {
            eventUseCases.update(state.eventFormItem.value)
        // if create, insert
        } else {
            eventUseCases.insert(state.eventFormItem.value)
        }
        emitSideEffect(EventFormScreenEvent.SideEffectEvent.NavigateUp)
    }
}