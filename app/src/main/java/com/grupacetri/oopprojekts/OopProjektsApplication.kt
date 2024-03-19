package com.grupacetri.oopprojekts

import android.app.Application
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.create
import com.grupacetri.oopprojekts.featureExample.di.ExampleComponent
import com.grupacetri.oopprojekts.featureExample.di.create

class OopProjektsApplication: Application() {
    val databaseComponent = DatabaseComponent::class.create(this)
    val exampleComponent = ExampleComponent::class.create(databaseComponent)
    override fun onCreate() {
        super.onCreate()
    }
}