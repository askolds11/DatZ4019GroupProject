package com.grupacetri.oopprojekts.featureEvent.domain

import kotlinx.datetime.Instant
import kotlin.time.Duration

data class EventHistoryItem(
    val id: Long,
    val name: String,
    val timeCreated: Instant,
    val timeEnded: Instant?,
    val diff: Duration
)

//fun Select.toEventHistoryItem(): EventHistoryItem {
//    return EventHistoryItem(this.id, this.name, this.time_started, this.time_ended ?: "", this.diff)
//}
//
