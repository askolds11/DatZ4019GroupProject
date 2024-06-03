package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureHistory.data.EventTimeInstanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class EventUseCases(
    private val eventRepository: EventRepository,
    private val eventTimeInstanceRepository: EventTimeInstanceRepository
) {
    /**
     * Inserts the passed [EventFormItem] item into the database
     * @param event Instance of a new [EventFormItem]
     * @return true if insert was successful, false if validations fail
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
     * Updates the database using the passed [EventFormItem] item
     * @param event Updated item
     * @return true if update was successful, false if validations fail
     */
    fun update(event: EventFormItem): Boolean {
        if (validate(event) == false) {
            return false
        }
        stopTracking(event.id)
        eventRepository.update(event.toEvent())
        return true
    }

    /**
     * Delete event and it's related eventTimeInstance's from database
     * @param id Id of item to be deleted
     */
    fun delete(id: Long) {
        eventRepository.delete(id)
    }

    /**
     * Creates a new [EventTimeInstance] with the passed [eventId]
     */
    fun startTracking(eventId: Long) {
        val eventTimeInstance = EventTimeInstance(
            0,
            eventId,
            Clock.System.now().toString(),
            null,
            Clock.System.now().toString(),
            Clock.System.now().toString()
        )
        eventTimeInstanceRepository.insert(eventTimeInstance)
    }

    /**
     * Stops all started [EventTimeInstance]
     * @param eventId Event's id
     */
    fun stopTracking(eventId: Long) {
        val timeEnded: String = Clock.System.now().toString()
        eventTimeInstanceRepository.updateTimeEnded(eventId, timeEnded)
    }

    /**
     * Gets [EventFormItem] by id
     * @param id id of the event
     */
    fun getById(id: Long): Flow<EventFormItem> {
        return eventRepository.selectById(id).map {
            it.toEventFormItem()
        }
    }

    /**
     * Gets all events that are started (have an [EventTimeInstance] with [EventTimeInstance.time_ended] null)
     */
    fun getStarted(): Flow<List<EventItem>> {
        return eventRepository.selectStarted().map {
            it.map { event ->
                event.toEventItem()
            }
        }
    }

    /**
     * Gets all events with a boolean that represents whether the even is started
     */
    fun getBit(): Flow<List<EventItem>> {
        return eventRepository.selectBit().map {
            it.map { event ->
                event.toEventItem()
            }
        }
    }

    /**
     * Gets all events that are inactive
     */
    fun getInactive(): Flow<List<EventItem>> {
        return eventRepository.selectInactive().map {
            it.map { event ->
                event.toEventItem()
            }
        }
    }

    /**
     * Validate's whether the [EventFormItem] item is correct
     * @param event Item to validate
     */
    fun validate(event: EventFormItem): Boolean {
        if (validateEventName(event.name) != null) {
            return false
        }
        if (validateEventColor(event.color) != null) {
            return false
        }
        return true
    }

    /**
     * Validates the [EventFormItem.name].
     * @param name the value for the [EventFormItem.name].
     * @return null or [EventNameError].
     */
    fun validateEventName(name: String): EventNameError? {
        if (name.isBlank()) {
            return EventNameError.IS_EMPTY
        }

        if (name.trim().length > NAME_MAX_LENGTH) {
            return EventNameError.TOO_LONG
        }
        return null
    }

    enum class EventNameError {
        IS_EMPTY,
        TOO_LONG,
    }

    /**
     * Validates the event.[EventFormItem.color].
     * @param colorHex the value for the [EventFormItem.color].
     * @return null or [EventNameError].
     */
    fun validateEventColor(colorHex: String): EventColorError? {
        val regex = Regex("^#[0-9a-fA-F]{6}\$")
        if (colorHex.isBlank()) {
            return EventColorError.IS_EMPTY
        }

        if (!regex.matches(colorHex.trim())) {
            return EventColorError.INVALID
        }

        return null
    }

    enum class EventColorError {
        IS_EMPTY,
        INVALID
    }

    companion object {
        const val NAME_MAX_LENGTH = 100
    }
}