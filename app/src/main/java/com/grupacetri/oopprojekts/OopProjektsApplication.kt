package com.grupacetri.oopprojekts

import android.app.Application
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.create
import com.grupacetri.oopprojekts.featureExample.di.ExampleComponent
import com.grupacetri.oopprojekts.featureExample.di.create

// application - always exists when app is alive
class OopProjektsApplication: Application() {
    // provide instances of components - singletons since they exist once
    val databaseComponent = DatabaseComponent::class.create(this)
    val exampleComponent = ExampleComponent::class.create(databaseComponent)
}