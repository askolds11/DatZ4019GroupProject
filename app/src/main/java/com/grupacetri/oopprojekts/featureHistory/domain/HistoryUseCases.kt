package com.grupacetri.oopprojekts.featureHistory.domain

import com.grupacetri.oopprojekts.featureHistory.data.EventTimeInstanceRepository
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

class HistoryUseCases(
    private val eventTimeInstanceRepository: EventTimeInstanceRepository
) {
    /**
     * Updates the database using the passed [HistoryFormItem] item
     * @param historyFormItem Updated item
     * @return true if update was successful, false if validations fail
     */
    fun updateTimeInstance(historyFormItem: HistoryFormItem): Boolean {
        if (validate(historyFormItem) == false) {
            return false
        }

        eventTimeInstanceRepository.update(historyFormItem.toEventTimeInstance())
        return true
    }

    /**
     * Get flow of [HistoryItem] which covers [date]
     * @param date Day to get history for
     */
    fun getHistory(date: LocalDateTime): Flow<List<HistoryItem>> {
        // Emit from flow each second so Ui can refresh each second
        val timeFlow = flow {
            while (true) {
                emit(Clock.System.now())
                delay(1000)
            }
        }
        // timeStarted must be <= this day, so end of this day
        val startTime = LocalTime(hour = 23, minute = 59, second = 59)
        // convert to UTC as that's what the database uses
        val newFilterTimeStarted =
            date.date.atTime(startTime).toInstant(TimeZone.currentSystemDefault()).toString()
        // timeEnded must be >= this day, so start of this day
        val endTime = LocalTime(hour = 0, minute = 0)
        // convert to UTC as that's what the database uses
        val newFilterTimeEnded =
            date.date.atTime(endTime).toInstant(TimeZone.currentSystemDefault()).toString()

        // combine time and database item flows
        return eventTimeInstanceRepository.getList(newFilterTimeStarted, newFilterTimeEnded)
            .combine(timeFlow) { list, time ->
                list.map { select ->
                    // convert to datetime
                    val timeStarted = Instant.parse(select.time_started)
                    val timeEnded = select.time_ended?.let { Instant.parse(it) }

                    // return resulting HistoryItem
                    HistoryItem(
                        select.id,
                        select.name,
                        timeStarted,
                        timeEnded,
                        (timeEnded ?: time) - timeStarted
                    )
                }
            }
    }

    /**
     * Get [HistoryFormItem] by id
     * @param id id of [HistoryFormItem]
     */
    fun selectById(id: Long): Flow<HistoryFormItem> {
        return eventTimeInstanceRepository.select(id).map {
            it.toHistoryFormItem()
        }
    }

    /**
     * Validates whether a [HistoryFormItem]'s time is correct
     */
    fun validateHistoryTime(time: String?): EventTimeError? {
        if (time.isNullOrBlank()) {
            return EventTimeError.IS_EMPTY
        }
        if (!isDateStringValid(time)) {
            return EventTimeError.NOT_DATETIME
        }
        return null
    }

    enum class EventTimeError {
        IS_EMPTY,
        NOT_DATETIME
    }

    /**
     * Validate's whether the [HistoryFormItem] item is correct
     * @param historyFormItem Item to validate
     */
    fun validate(historyFormItem: HistoryFormItem): Boolean {
        if (validateHistoryTime(historyFormItem.timeStarted) != null) {
            return false
        }
        if (validateHistoryTime(historyFormItem.timeEnded) != null) {
            return false
        }
        return true
    }

    /**
     * Checks if the given string is a valid date string text wise
     */
    private fun isDateStringValid(dateString: String): Boolean {
        return try {
            Instant.parse(dateString).toString() == dateString
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}