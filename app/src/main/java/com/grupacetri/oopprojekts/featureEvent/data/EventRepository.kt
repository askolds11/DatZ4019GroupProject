package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.EventSelectWithBit
import kotlinx.coroutines.flow.Flow
interface EventRepository {
    fun insert(event: Event)
    fun update(event: Event)
    fun selectStarted(): Flow<List<Event>>
    fun selectBit(): Flow<List<EventSelectWithBit>>
    fun selectById(id: Long): Flow<Event>
    fun selectInactive(): Flow<List<Event>>
}

