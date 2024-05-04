package com.grupacetri.oopprojekts.featureEvent.domain

import com.grupacetri.oopprojekts.featureEvent.data.EventRepository
import com.grupacetri.oopprojekts.featureEvent.domain.EventFormItem
import com.grupacetri.oopprojekts.featureEvent.domain.toEventFormItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventUseCases(
    private val eventRepository: EventRepository
) {
//    fun getList(): Flow<List<FooItem>> {
//        return fooRepository.getList().map {
//            it.mapIndexed { _, foo ->
//                foo.toFooItem()
//            }
//        }
//    }

//    fun delete(id: Long) {
//        fooRepository.delete(id)
//    }

    fun insert(event: EventFormItem) {
        eventRepository.insert(event.toEvent())
    }

}