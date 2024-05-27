package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.SelectBit
import com.grupacetri.oopprojekts.core.toLong

data class EventFormItem(
    val id: Long,
    val name: String,
    val comment: String?,
    val color: String,
    val active: Boolean,
    val started: Boolean,
    private val created: String,
    private val modified: String
) {
    fun toEvent(): Event {
        return Event(id, name.trim(), comment?.trim(), color.trim(), active.toLong(), created, modified)
    }
}

fun Event.toEventFormItem(): EventFormItem {
    return EventFormItem(
        this.id,
        this.name,
        this.comment,
        this.color,
        this.active == 1L,
        false,
        this.created,
        this.modified
    )
}

fun SelectBit.toEventFormItem(): EventFormItem {
    return EventFormItem(
        this.id,
        this.name,
        this.comment,
        this.color,
        this.active == 1L,
        this.bit,
        this.created,
        this.modified
    )
}