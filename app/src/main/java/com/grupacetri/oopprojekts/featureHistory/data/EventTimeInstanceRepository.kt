package com.grupacetri.oopprojekts.featureHistory.data

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.HistorySelectByDate
import kotlinx.coroutines.flow.Flow

interface EventTimeInstanceRepository {
    fun insert(eventTimeInstance: EventTimeInstance)
    fun update(eventTimeInstance: EventTimeInstance)
    fun getList(filterTimeStarted:String, filterTimeEnded:String): Flow<List<HistorySelectByDate>>
    fun updateTimeEnded(eventId: Long, timeEnded: String)
    fun select(id: Long): Flow<EventTimeInstance>
}