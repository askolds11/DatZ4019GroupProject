package com.grupacetri.oopprojekts.featureEvent.ui.history

import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.ui.sideeffect.SideEffectViewModel
import com.grupacetri.oopprojekts.featureEvent.domain.EventUseCases
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
class EventHistoryScreenViewModel(
    private val settingsUseCases: SettingsUseCases,
    private val eventUseCases: EventUseCases
    ) : SideEffectViewModel<EventHistoryScreenEvent.SideEffectEvent>() {
        val state = EventHistoryScreenState()
        val dateFlow = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        private val settingFlow = settingsUseCases.getSetting(AllSettings.TimeDiffFormat())
        private fun formatDateTime(dateTime: LocalDateTime): String {
            val year = dateTime.year.toString().padStart(4, '0')
            val month = dateTime.monthNumber.toString().padStart(2, '0')
            val day = dateTime.dayOfMonth.toString().padStart(2, '0')
            val hour = dateTime.hour.toString().padStart(2, '0')
            val minute = dateTime.minute.toString().padStart(2, '0')
            val second = dateTime.second.toString().padStart(2, '0')

            return "$year-$month-$day $hour:$minute:$second"
        }
        @OptIn(ExperimentalCoroutinesApi::class)
        val eventHistoryFlow: SharedFlow<Unit> =
                dateFlow.flatMapLatest {date ->
                    state.date.value = date
                    settingFlow.combine(eventUseCases.getHistory(date))
                    {
                            setting,it ->
                        val items = it.map { item ->
                            val timeCreatedLocal = formatDateTime(item.timeCreated.toLocalDateTime(TimeZone.currentSystemDefault()))
                            val timeEndedLocal = item.timeEnded?.let { formatDateTime(it.toLocalDateTime(TimeZone.currentSystemDefault())) } ?: ""
                            val diff = when(setting.format){
                                AllSettings.TimeDiffFormat.TimeDiffFormatValue.Minutes -> {
                                    item.diff.inWholeMinutes.toString() + " minutes"
                                }
                                AllSettings.TimeDiffFormat.TimeDiffFormatValue.Seconds -> {
                                    item.diff.inWholeSeconds.toString() + " seconds"
                                }
                            }

                            EventHistoryUiItem(
                                item.id,
                                item.name,
                                timeCreatedLocal,
                                timeEndedLocal,
                                diff
                            )
                        }
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

    fun onEvent(event: EventHistoryScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is EventHistoryScreenEvent.SideEffectEvent -> emitSideEffect(event)
            EventHistoryScreenEvent.NextDay -> nextDay()
            EventHistoryScreenEvent.PreviousDay -> previousDay()
        }
    }
//
    private fun previousDay() {
        viewModelScope.launch{
            dateFlow.emit((state.date.value.toInstant(TimeZone.currentSystemDefault()) - 24.hours).toLocalDateTime(TimeZone.currentSystemDefault()))
        }
    }
    private fun nextDay() {
        viewModelScope.launch{
            dateFlow.emit((state.date.value.toInstant(TimeZone.currentSystemDefault()) + 24.hours).toLocalDateTime(TimeZone.currentSystemDefault()))
        }
    }

//    private fun startTracking(id: Long) {
//        eventUseCases.start_tracking(id)
//    }
}