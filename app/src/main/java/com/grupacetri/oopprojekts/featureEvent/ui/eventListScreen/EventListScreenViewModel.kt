package com.grupacetri.oopprojekts.featureEvent.ui.eventListScreen

import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
class EventListScreenViewModel(
    private val eventUseCases: EventUseCases
) : SideEffectViewModel<EventListScreenEvent.SideEffectEvent>() {
    val state = EventListScreenState()

    // Event list with boolean
    private val eventListFlow: Flow<Unit> = eventUseCases.getBit()
        .map {
            state.eventList.clear()
            state.eventList.addAll(it)
            return@map //Unit
        }

    // Started event list
    private val startedEventListFlow: Flow<Unit> = eventUseCases.getStarted()
            .map {
                state.startedEventList.clear()
                state.startedEventList.addAll(it)
                return@map //Unit
            }

    // Inactive event list
    private val inactiveEventListFlow: Flow<Unit> = eventUseCases.getInactive()
        .map {
            state.inactiveEventList.clear()
            state.inactiveEventList.addAll(it)
            return@map //Unit
        }

    // Flow that Ui needs to collect
    val eventListUiFlow: SharedFlow<Unit> = merge(eventListFlow, startedEventListFlow, inactiveEventListFlow)
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )

    fun onEvent(event: EventListScreenEvent) {
        when (event) {
            is EventListScreenEvent.StartTracking -> startTracking(event.id)
            is EventListScreenEvent.StopTracking -> stopTracking(event.id)
            is EventListScreenEvent.SideEffectEvent -> emitSideEffect(event) // Handles both create and edit event.
        }
    }

    private fun startTracking(id: Long) {
        eventUseCases.startTracking(id)
    }

    private fun stopTracking(id: Long) {
        eventUseCases.stopTracking(id)
    }

}