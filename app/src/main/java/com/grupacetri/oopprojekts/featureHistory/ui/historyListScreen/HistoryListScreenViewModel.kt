package com.grupacetri.oopprojekts.featureHistory.ui.historyListScreen

import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureHistory.domain.HistoryUseCases
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import com.grupacetri.oopprojekts.featureSettings.domain.SettingsUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import me.tatarka.inject.annotations.Inject
import kotlin.time.Duration.Companion.hours

@Inject
class HistoryListScreenViewModel(
    private val settingsUseCases: SettingsUseCases,
    private val historyUseCases: HistoryUseCases
) : SideEffectViewModel<HistoryListScreenEvent.SideEffectEvent>() {
    val state = HistoryListScreenState()

    /** Selected date **/
    private val dateFlow =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))

    /** Setting for time diff format **/
    private val timeDiffFormatSettingFlow =
        settingsUseCases.getSetting(AllSettings.TimeDiffFormat())

    /** Format LocalDateTime to string **/
    private fun formatDateTime(dateTime: LocalDateTime): String {
        val year = dateTime.year.toString().padStart(4, '0')
        val month = dateTime.monthNumber.toString().padStart(2, '0')
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')
        val second = dateTime.second.toString().padStart(2, '0')

        return "$year-$month-$day $hour:$minute:$second"
    }

    /**
     * Flow that needs to be collected from Ui with lifecycle - combines previous flows
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val eventHistoryFlow: SharedFlow<Unit> = dateFlow.flatMapLatest { date ->
        // Update state.date on each date change
        state.date.value = date
        // pass time diff format to history item flow
        timeDiffFormatSettingFlow.combine(historyUseCases.getHistory(date)) { setting, it ->
            // Update state.timeDiffFormat on each timeDiffFormat change
            state.timeDiffFormat.value = setting
            // map from HistoryItem to HistoryListUiItem
            val items = it.map { item ->
                // format datetimes, time
                val timeCreatedLocal =
                    formatDateTime(item.timeCreated.toLocalDateTime(TimeZone.currentSystemDefault()))
                val timeEndedLocal =
                    item.timeEnded?.let { formatDateTime(it.toLocalDateTime(TimeZone.currentSystemDefault())) }
                        ?: ""
                val diff = when (setting.format) {
                    AllSettings.TimeDiffFormat.TimeDiffFormatValue.Minutes -> {
                        item.diff.inWholeMinutes.toString()
                    }

                    AllSettings.TimeDiffFormat.TimeDiffFormatValue.Seconds -> {
                        item.diff.inWholeSeconds.toString()
                    }
                }

                // finally create HistoryListUiItem for map
                HistoryListUiItem(
                    item.id,
                    item.name,
                    timeCreatedLocal,
                    timeEndedLocal,
                    diff
                )
            }

            // update list
            state.eventHistoryList.clear()
            state.eventHistoryList.addAll(
                items
            )
            return@combine //Unit
        }
    }.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000)
    )

    fun onEvent(event: HistoryListScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is HistoryListScreenEvent.SideEffectEvent -> emitSideEffect(event)
            HistoryListScreenEvent.NextDay -> nextDay()
            HistoryListScreenEvent.PreviousDay -> previousDay()
        }
    }

    // change date to previous day
    private fun previousDay() {
        viewModelScope.launch {
            dateFlow.emit(
                (state.date.value.toInstant(TimeZone.currentSystemDefault()) - 24.hours).toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
            )
        }
    }

    // change date to next day
    private fun nextDay() {
        viewModelScope.launch {
            dateFlow.emit(
                (state.date.value.toInstant(TimeZone.currentSystemDefault()) + 24.hours).toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
            )
        }
    }
}