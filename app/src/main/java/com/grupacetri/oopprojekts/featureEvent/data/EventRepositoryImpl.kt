package com.grupacetri.oopprojekts.featureEvent.data

import app.cash.sqldelight.coroutines.asFlow
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl (
    private val database: Database
): EventRepository {
    override fun getList(): Flow<List<Event>> { //return type is Unit
        return database.eventQueries.selectlist().asFlow().map{
            it.executeAsList()
        }
    }
    override fun insert(event: Event) {
        database.eventQueries.insert(event)
    }


//    override fun select(id: Long) {
//        database.eventQueries.select(id)
//    }

//    override fun update(name: String, comment: String?, color: String, active: Long, modified: String) {
//        database.eventQueries.update(name, comment, color, active, modified)
//    }
//    override fun delete(id: Long) {
//        database.eventQueries.delete(id)
//    }
}

