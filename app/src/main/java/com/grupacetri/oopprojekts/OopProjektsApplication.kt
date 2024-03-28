package com.grupacetri.oopprojekts

import android.app.Application
import com.grupacetri.oopprojekts.core.di.DataComponent
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.DomainComponent
import com.grupacetri.oopprojekts.core.di.UIComponent
import com.grupacetri.oopprojekts.core.di.create

// application - always exists when app is alive
class OopProjektsApplication: Application() {
    // provide instances of components - singletons since they exist once
    val databaseComponent = DatabaseComponent::class.create(this)
    val dataComponent = DataComponent::class.create(databaseComponent)
    val domainComponent = DomainComponent::class.create(dataComponent)
    val uiComponent = UIComponent::class.create(domainComponent)
}