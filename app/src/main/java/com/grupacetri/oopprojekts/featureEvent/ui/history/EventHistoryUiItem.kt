package com.grupacetri.oopprojekts.featureEvent.ui.history

data class EventHistoryUiItem(
    val id: Long,
    val name: String,
    val timeCreated: String,
    val timeEnded: String,
    val diff: String
)

