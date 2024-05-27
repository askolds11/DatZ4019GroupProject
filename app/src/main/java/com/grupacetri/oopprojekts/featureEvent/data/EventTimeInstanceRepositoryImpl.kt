package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.EventTimeInstance

class EventTimeInstanceRepositoryImpl (
    private val database: Database
): EventTimeInstanceRepository {
//    override fun getList(): Flow<List<Event>> { //return type is Unit
//        return database.eventQueries.selectlist().asFlow().map{
//            it.executeAsList()
//        }
//    }
    override fun insert(eventTimeInstance: EventTimeInstance) {
        database.eventTimeInstanceQueries.insert(eventTimeInstance)
    }

    override fun updateTimeEnded(eventId: Long, timeEnded: String) {
        database.eventTimeInstanceQueries.updateTimeEnded(timeEnded, eventId)
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

