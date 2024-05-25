package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.SelectBit

data class EventItem(
    val id: Long,
    val name: String,
    val color: String,
    val active: Boolean,
    val started: Boolean
)
fun Event.toEventItem(): EventItem {
    return EventItem(
        this.id,
        this.name,
        this.color,
        this.active == 1L,
        false
    )
}

fun SelectBit.toEventItem(): EventItem {
    return EventItem(
        this.id,
        this.name,
        this.color,
        this.active == 1L,
        this.bit
    )
}