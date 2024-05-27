package com.grupacetri.oopprojekts

import android.app.Application
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.create
import com.grupacetri.oopprojekts.featureEvent.di.EventComponent
import com.grupacetri.oopprojekts.featureEvent.di.create
import com.grupacetri.oopprojekts.featureFoo.di.FooComponent
import com.grupacetri.oopprojekts.featureFoo.di.create

class OopProjektsApplication: Application() {
    private val databaseComponent = DatabaseComponent::class.create(this)
    val fooComponent = FooComponent::class.create(databaseComponent)
    val eventComponent = EventComponent::class.create(databaseComponent)
}