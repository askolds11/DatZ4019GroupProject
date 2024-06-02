package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.EventTimeInstance

data class EventTimeInstanceFormItem(
    val id: Long,
    private val eventId: Long,
    val timeStarted: String,
    val timeEnded: String?,
    private val created: String,
    private val modified: String,
) {
    fun toEventTimeInstance(): EventTimeInstance {
        return EventTimeInstance(id, eventId, timeStarted, timeEnded, created, modified)
    }
}



fun EventTimeInstance.toEventTimeInstanceFormItem(): EventTimeInstanceFormItem {
    return EventTimeInstanceFormItem(this.id, this.event_id, this.time_started, this.time_ended, this.created, this.modified)
}

