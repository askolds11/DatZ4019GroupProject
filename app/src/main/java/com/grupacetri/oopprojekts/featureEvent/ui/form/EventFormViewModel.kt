package com.grupacetri.oopprojekts.featureEvent.ui.form

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.grupacetri.oopprojekts.core.ui.navigation.EventNavigationRoute
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureEvent.ui.list.EventListScreenEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject


@Inject
//@FooScope
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
            is EventFormScreenEvent.Save -> save()
            is EventFormScreenEvent.SideEffectEvent -> emitSideEffect(event)
        }
    }

    private fun updateColor(color: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(color = color)
    }

    private fun updateComment(comment: String) {
        state.eventFormItem.value = state.eventFormItem.value.copy(comment = comment)
    }

    private fun updateName(name: String) {
        val validation = eventUseCases.validateEventName(name)
        state.eventFormItem.value = state.eventFormItem.value.copy(name = name)
        state.nameValidation.value = validation
    }

    private fun save() {
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