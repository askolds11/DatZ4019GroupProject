package com.grupacetri.oopprojekts.featureEvent.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.featureEvent.di.EventScope
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
import com.grupacetri.oopprojekts.featureEvent.ui.form.EventFormScreenEvent
import com.grupacetri.oopprojekts.featureFoo.di.FooScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
@EventScope
class EventListScreenViewModel(
    private val eventUseCases: EventUseCases
    ) : ViewModel() {
        val state = EventListScreenState()

        val eventListFlow: SharedFlow<Unit> = eventUseCases.getList()
            .map {
                state.eventList.clear()
                state.eventList.addAll(it)
                return@map //Unit
            }.shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000)
            )

    fun onEvent(event: EventListScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventListScreenEvent.StartTracking -> startTracking(event.id)
        }
    }

    private fun startTracking(id: Long) {
        eventUseCases.start_tracking(id)
    }
}