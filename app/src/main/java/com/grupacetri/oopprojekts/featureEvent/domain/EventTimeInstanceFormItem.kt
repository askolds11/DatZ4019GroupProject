package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.Select
import com.grupacetri.oopprojekts.core.toLong

data class EventTimeInstanceFormItem(
    val id: Long,
    val time_created: String,
    val time_ended: String
)

fun EventTimeInstance.toEventTimeInstanceFormItem(): EventTimeInstanceFormItem {
    return EventTimeInstanceFormItem(this.id, this.time_started, this.time_ended ?: "")
}

