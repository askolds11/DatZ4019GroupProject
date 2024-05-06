package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventTimeInstanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

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

    fun update(event: EventFormItem): Boolean {
        if (validate(event) == false) {
            return false
        }
        eventRepository.update(event.toEvent())
        return true
    }

    /**
     * Name error enums.
     */
    enum class EventNameError {
        IS_EMPTY,
        TOO_LONG,
    }

    fun startTracking(eventId: Long) {
        val eventTimeInstance: EventTimeInstance = EventTimeInstance(0, eventId, Clock.System.now().toString(), null, Clock.System.now().toString(), Clock.System.now().toString())
        eventTimeInstanceRepository.insert(eventTimeInstance)
    }

    fun getHistory(): Flow<List<EventHistoryItem>> {
        return eventTimeInstanceRepository.getList().map {
            it.mapIndexed { _, select ->
                select.toEventHistoryItem()
            }
        }
    }
    fun stopTracking(eventId: Long) {
        val timeEnded: String = Clock.System.now().toString()
        eventTimeInstanceRepository.updateTimeEnded(eventId, timeEnded)
    }

    fun getStarted(): Flow<List<EventItem>> {
        return eventRepository.selectStarted().map{
            it.map { event ->
                event.toEventItem()
            }
        }
    }

    fun getBit(): Flow<List<EventItem>> {
        return eventRepository.selectBit().map{
            it.map { event ->
                event.toEventItem()
            }
        }
    }

    fun getById(id: Long): Flow<EventFormItem> {
        return eventRepository.selectById(id).map{
            it.toEventFormItem()
        }
    }


}