package com.grupacetri.oopprojekts.featureEvent.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.featureEvent.di.EventScope
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
@EventScope
class EventListScreenViewModel(
    private val eventUseCases: EventUseCases
    ) : ViewModel() {
        val state = EventListScreenState()

//        val eventListFlow: SharedFlow<Unit> = eventUseCases.getList()
//            .map {
//                state.eventList.clear()
//                state.eventList.addAll(it)
//                return@map //Unit
//            }.shareIn(
//                viewModelScope,
//                SharingStarted.WhileSubscribed(5000)
//            )
        private val eventListFlow: Flow<Unit> = eventUseCases.getBit()
            .map {
                state.eventList.clear()
                state.eventList.addAll(it)
                return@map //Unit
            }
        private val startedEventListFlow: Flow<Unit> = eventUseCases.getStarted()
            .map {
                state.startedEventList.clear()
                state.startedEventList.addAll(it)
                return@map //Unit
            }

        val settingsFlow: SharedFlow<Unit> = merge(eventListFlow, startedEventListFlow)
            .shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000)
            )

    fun onEvent(event: EventListScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventListScreenEvent.StartTracking -> startTracking(event.id)
            is EventListScreenEvent.StopTracking -> stopTracking(event.id)
        }
    }

    private fun startTracking(id: Long) {
        eventUseCases.startTracking(id)
    }

    private fun stopTracking(id: Long) {
        eventUseCases.stopTracking(id)
    }

}