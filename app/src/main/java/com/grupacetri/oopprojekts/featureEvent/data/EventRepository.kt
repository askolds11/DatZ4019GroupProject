package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getList(): Flow<List<Event>>
    fun insert(event: Event)



    //fun delete(id: Int)


    //fun select(id: Int)

    //fun insert(name: String, comment: String?, color: String?, active: Int?, created: String, modified: String)



    //fun update(id: Int, name: String, comment: String?, color: String, active: Int, modified: String)


//    fun insert(data: Type?, data: Type?, data: Type?)
//    fun update(data: Type?, data: Type?, data: Type?)
}