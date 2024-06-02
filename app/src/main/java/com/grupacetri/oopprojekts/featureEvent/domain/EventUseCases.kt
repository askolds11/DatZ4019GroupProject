package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.data.EventTimeInstanceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

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


    private fun isDateStringValid(dateString: String): Boolean {
        return try {
            Instant.parse(dateString).toString() == dateString
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun validateEventTime(time: String?): EventTimeError? {
        if (time.isNullOrBlank()) {
            return EventTimeError.IS_EMPTY
        }
        if (!isDateStringValid(time)) {
            return EventTimeError.NOT_DATETIME
        }
        return null
    }

    fun validate(event: EventTimeInstanceFormItem): Boolean {
        if (validateEventTime(event.timeStarted) != null) {
            return false
        }
        if (validateEventTime(event.timeEnded) != null) {
            return false
        }
        return true
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
        stopTracking(event.id)
        eventRepository.update(event.toEvent())
        return true
    }

    fun updateTimeInstance(eventTimeInstance: EventTimeInstanceFormItem): Boolean {
//        if (validate(eventTimeInstance) == false) {
//            return false
//        }

        eventTimeInstanceRepository.update(eventTimeInstance.toEventTimeInstance())
        return true
    }

    /**
     * Name error enums.
     */
    enum class EventNameError {
        IS_EMPTY,
        TOO_LONG,
    }

    enum class EventColorError {
        IS_EMPTY,
        INVALID
    }

    enum class EventTimeError {
        IS_EMPTY,
        NOT_DATETIME
    }

    fun startTracking(eventId: Long) {
        val eventTimeInstance: EventTimeInstance = EventTimeInstance(0, eventId, Clock.System.now().toString(), null, Clock.System.now().toString(), Clock.System.now().toString())
        eventTimeInstanceRepository.insert(eventTimeInstance)
    }

    fun getHistory(date:LocalDateTime): Flow<List<EventHistoryItem>> {
        val timeFlow = flow {
            while (true) {
                emit(Clock.System.now())
                delay(1000)
            }
        }
        val startTime = LocalTime(hour=23,minute=59,second=59)
        val newFilterTimeStarted = date.date.atTime(startTime).toInstant(TimeZone.currentSystemDefault()).toString()
        val endTime = LocalTime(hour=0,minute=0)
        val newFilterTimeEnded = date.date.atTime(endTime).toInstant(TimeZone.currentSystemDefault()).toString()


        return eventTimeInstanceRepository.getList(newFilterTimeStarted, newFilterTimeEnded).combine(timeFlow) { list, time ->

            list.map {select ->
                val timeStarted = Instant.parse(select.time_started)
                val timeEnded = select.time_ended?.let{Instant.parse(it)}
                EventHistoryItem(
                    select.id,
                    select.name,
                    timeStarted,
                    timeEnded,
                    (timeEnded ?: time) - timeStarted
                )
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

    fun selectById(id: Long): Flow<EventTimeInstanceFormItem> {
        return eventTimeInstanceRepository.select(id).map {
            it.toEventTimeInstanceFormItem()
        }
    }

    fun getInactive(): Flow<List<EventItem>> {
        return eventRepository.selectInactive().map{
            it.map { event ->
                event.toEventItem()
            }
        }
    }

}