package com.grupacetri.oopprojekts.featureEvent.data

import app.cash.sqldelight.coroutines.asFlow
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.Select
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getList(filterTimeStarted:String, filterTimeEnded:String): Flow<List<Select>> { //return type is Unit
        return database.eventTimeInstanceQueries.select(filterTimeStarted, filterTimeEnded).asFlow().map{
            it.executeAsList()
        }
    }
    override fun updateTimeEnded(eventId: Long, timeEnded: String) {
        database.eventTimeInstanceQueries.updateTimeEnded(timeEnded, eventId)
    }

    override fun update(eventTimeInstance: EventTimeInstance) {
        database.eventTimeInstanceQueries.update(eventTimeInstance)
    }

    override fun select(id: Long): Flow<EventTimeInstance> {
        return database.eventTimeInstanceQueries.selectById(id).asFlow().map{
            it.executeAsOne()
        }
    }

//    override fun update(name: String, comment: String?, color: String, active: Long, modified: String) {
//        database.eventQueries.update(name, comment, color, active, modified)
//    }
//    override fun delete(id: Long) {
//        database.eventQueries.delete(id)
//    }
}

