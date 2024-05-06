package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventTimeInstanceRepository
import com.grupacetri.oopprojekts.featureEvent.domain.EventFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.toEventFormItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.time.LocalDateTime

class EventUseCases(
    private val eventRepository: EventRepository,
    private val eventTimeInstanceRepository: EventTimeInstanceRepository
) {
    fun getList(): Flow<List<EventItem>> {
        return eventRepository.getList().map {
            it.mapIndexed { _, event ->
                event.toEventItem()
            }
        }
    }

//    fun delete(id: Long) {
//        fooRepository.delete(id)
//    }

    fun validate(event: EventFormItem): Boolean {
        if (validateEventName(event.name) != null) {
            return false
        }
        return true
    }

    /**
     * Validates the event.name.
     * @param name the value for the event.name.
     * @return null or EventNameError.
     */
    fun validateEventName(name: String): EventNameError? {
        if (name.isBlank()) {
            return EventNameError.IS_EMPTY
        }

        if (name.trim().length > 100) {
            return EventNameError.TOO_LONG
        }
        return null
    }

    /**
     * @author @Agnese Grike
     */
    fun insert(event: EventFormItem): Boolean {
        if (validate(event) == false) {
            return false
        }
        eventRepository.insert(event.toEvent())
        return true
    }

    /**
     * Name error enums.
     */
    enum class EventNameError {
        IS_EMPTY,
        TOO_LONG,
    }

    fun start_tracking(event_id: Long) {
        val eventTimeInstance: EventTimeInstance = EventTimeInstance(0, event_id, Clock.System.now().toString(), null, Clock.System.now().toString(), Clock.System.now().toString())
        eventTimeInstanceRepository.insert(eventTimeInstance)
    }

}