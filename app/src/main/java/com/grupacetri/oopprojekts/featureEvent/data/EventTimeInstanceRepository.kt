package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.Select
import kotlinx.coroutines.flow.Flow

interface EventTimeInstanceRepository {
//    fun getList(): Flow<List<Event>>
    fun insert(eventTimeInstance: EventTimeInstance)

    fun getList(): Flow<List<Select>>
    fun updateTimeEnded(eventId: Long, timeEnded: String)

    //fun delete(id: Int)


    //fun select(id: Int)

    //fun insert(name: String, comment: String?, color: String?, active: Int?, created: String, modified: String)



    //fun update(id: Int, name: String, comment: String?, color: String, active: Int, modified: String)


//    fun insert(data: Type?, data: Type?, data: Type?)
//    fun update(data: Type?, data: Type?, data: Type?)
}