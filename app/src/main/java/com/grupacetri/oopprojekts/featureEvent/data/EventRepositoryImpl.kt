package com.grupacetri.oopprojekts.featureEvent.data

import app.cash.sqldelight.coroutines.asFlow
import com.grupacetri.oopprojekts.Database
import com.grupacetri.oopprojekts.Event
import com.grupacetri.oopprojekts.EventSelectWithBit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl (
    private val database: Database
): EventRepository {
    override fun insert(event: Event) {
        database.eventQueries.eventInsert(event)
    }

    override fun update(event: Event) {
        database.eventQueries.eventUpdate(event)
    }

    override fun selectStarted(): Flow<List<Event>> {
        return database.eventQueries.eventSelectStarted().asFlow().map {
            it.executeAsList()
        }
    }

    override fun selectBit(): Flow<List<EventSelectWithBit>> {
        return database.eventQueries.eventSelectWithBit().asFlow().map {
            it.executeAsList()
        }
    }

    override fun selectById(id: Long): Flow<Event> {
        return database.eventQueries.eventSelectById(id).asFlow().map {
            it.executeAsOne()
        }
    }

    override fun selectInactive(): Flow<List<Event>> {
        return database.eventQueries.eventSelectInactive().asFlow().map {
            it.executeAsList()
        }
    }
}

