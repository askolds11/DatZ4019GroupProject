package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.Select
import com.grupacetri.oopprojekts.core.toLong

data class EventHistoryItem(
    val id: Long,
    val name: String,
    val time_created: String,
    val time_ended: String,
    val diff: Long
)

fun Select.toEventHistoryItem(): EventHistoryItem {
    return EventHistoryItem(this.id, this.name, this.time_started, this.time_ended ?: "", this.diff)
}

