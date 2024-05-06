package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.core.toLong

data class EventItem(
    val id: Long,
    val name: String,
    val color: String,
    val active: Boolean
)
fun Event.toEventItem(): EventItem {
    return EventItem(this.id, this.name, this.color, this.active == 1L)
}