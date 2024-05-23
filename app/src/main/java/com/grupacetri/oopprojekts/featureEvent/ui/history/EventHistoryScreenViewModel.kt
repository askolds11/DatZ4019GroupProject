package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.di.EventScope
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
@EventScope
class EventHistoryScreenViewModel(
    private val eventUseCases: EventUseCases
    ) : SideEffectViewModel<EventHistoryScreenEvent.SideEffectEvent>() {
        val state = EventHistoryScreenState()

        val eventHistoryFlow: SharedFlow<Unit> = eventUseCases.getHistory()
            .map {
                state.eventHistoryList.clear()
                state.eventHistoryList.addAll(it)
                return@map //Unit
            }.shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000)
            )

    fun onEvent(event: EventHistoryScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventHistoryScreenEvent.SideEffectEvent.NavigateToScreen999 -> emitSideEffect(event)
        }
    }
//
//    private fun startTracking(id: Long) {
//        eventUseCases.start_tracking(id)
//    }
}