package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Event

class EventRepositoryImpl (
    private val database: Database
): EventRepository {

//    override fun select(id: Long) {
//        database.eventQueries.select(id)
//    }
//    override fun getList() { //return type is Unit
//        database.eventQueries.select()
//    }
    override fun insert(event: Event) {
        database.eventQueries.insert(event)
    }
//    override fun update(name: String, comment: String?, color: String, active: Long, modified: String) {
//        database.eventQueries.update(name, comment, color, active, modified)
//    }
//    override fun delete(id: Long) {
//        database.eventQueries.delete(id)
//    }
}

