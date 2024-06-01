package com.grupacetri.oopprojekts

import android.app.Application
import com.grupacetri.oopprojekts.core.di.DatabaseComponent
import com.grupacetri.oopprojekts.core.di.create
import com.grupacetri.oopprojekts.featureEvent.di.EventComponent
import com.grupacetri.oopprojekts.featureEvent.di.create
import com.grupacetri.oopprojekts.featureSettings.di.SettingsComponent
import com.grupacetri.oopprojekts.featureSettings.di.create
import kotlinx.coroutines.MainScope

class OopProjektsApplication: Application() {
    private val databaseComponent = DatabaseComponent::class.create(this)
    val eventComponent = EventComponent::class.create(databaseComponent)
    val settingsComponent = SettingsComponent::class.create(databaseComponent, MainScope())
}