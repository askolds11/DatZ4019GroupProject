package com.grupacetri.oopprojekts.featureHistory.data

import app.cash.sqldelight.coroutines.asFlow
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.EventTimeInstance
import com.grupacetri.oopprojekts.HistorySelectByDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventTimeInstanceRepositoryImpl (
    private val database: Database
): EventTimeInstanceRepository {
    override fun insert(eventTimeInstance: EventTimeInstance) {
        database.eventTimeInstanceQueries.historyInsert(eventTimeInstance)
    }

    override fun getList(filterTimeStarted:String, filterTimeEnded:String): Flow<List<HistorySelectByDate>> {
        return database.eventTimeInstanceQueries.historySelectByDate(filterTimeStarted, filterTimeEnded).asFlow().map{
            it.executeAsList()
        }
    }

    override fun updateTimeEnded(eventId: Long, timeEnded: String) {
        database.eventTimeInstanceQueries.historyUpdateTimeEnded(timeEnded, eventId)
    }

    override fun update(eventTimeInstance: EventTimeInstance) {
        database.eventTimeInstanceQueries.historyUpdate(eventTimeInstance)
    }

    override fun select(id: Long): Flow<EventTimeInstance> {
        return database.eventTimeInstanceQueries.historySelectById(id).asFlow().map{
            it.executeAsOne()
        }
    }
}

