package com.grupacetri.oopprojekts.featureEvent.ui.form

import androidx.lifecycle.ViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import me.tatarka.inject.annotations.Inject


@Inject
//@FooScope
class EventFormViewModel (
    private val eventUseCases: EventUseCases
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
            is EventFormScreenEvent.Save -> save()
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
        eventUseCases.insert(state.eventFormItem.value)
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