package com.grupacetri.oopprojekts.featureEvent.data

import com.grupacetri.oopprojekts.Database

class EventRepositoryImpl (
    private val database: Database
): EventRepository {

    override fun select(id: Int) {
        database.eventQueries.select(id)
    }
//    override fun getList() { //return type is Unit
//        database.eventQueries.select()
//    }
    override fun insert(name: String, comment: String?, color: String?, active: Int?, created: String, modified: String) {
        database.eventQueries.insert(name, comment, color, active, created, modified)
    }
    override fun update(name: String, comment: String?, color: String, active: Int, modified: String) {
        database.eventQueries.update(name, comment, color, active, modified)
    }
    override fun delete(id: Int) {
        database.eventQueries.delete(id)
    }
}

