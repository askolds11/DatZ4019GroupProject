package com.grupacetri.oopprojekts.featureEvent.ui.form

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
class EventFormViewModel (
    private val eventUseCases: EventUseCases,
    @Assisted savedStateHandle: SavedStateHandle,
) : SideEffectViewModel<EventFormScreenEvent.SideEffectEvent>() {
    val state = EventFormScreenState()

    private val eventParams = savedStateHandle.toRoute<EventNavigationRoute.Event>()
    init {
//        Log.d("Test", "${eventParams.id}")
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        if (eventParams.id != 0L) {
            val eventItem = eventUseCases.getById(eventParams.id).first()
            state.eventFormItem.value = eventItem
        }
        state.isEditMode.value = (eventParams.id != 0L)
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
    fun onEvent(event: EventFormScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
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
    }


    private fun updateColor(color: String) {
        val validation = eventUseCases.validateEventColor(color)
        state.eventFormItem.value = state.eventFormItem.value.copy(color = color)
        state.colorValidation.value = validation
        checkValidations()
    }

    private fun updateActive(active: Boolean) {
        state.eventFormItem.value = state.eventFormItem.value.copy(active = active)
    }

    private fun checkValidations() {
        if (state.nameValidation.value != null || state.colorValidation.value != null) {
            state.saveEnabled.value = false
        } else {
            state.saveEnabled.value = true
        }
    }

    private fun save() {
        if (!eventUseCases.validate(state.eventFormItem.value)) {
            state.nameValidation.value = eventUseCases.validateEventName(state.eventFormItem.value.name)
            state.colorValidation.value = eventUseCases.validateEventColor(state.eventFormItem.value.color)
            return
        }
        if (eventParams.id != 0L) {
            eventUseCases.update(state.eventFormItem.value)
        } else {
            eventUseCases.insert(state.eventFormItem.value)
        }
        emitSideEffect(EventFormScreenEvent.SideEffectEvent.NavigateUp)
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