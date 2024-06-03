package com.grupacetri.oopprojekts.featureHistory.domain

import kotlinx.datetime.Instant
import kotlin.time.Duration

data class HistoryItem(
    val id: Long,
    val name: String,
    val timeCreated: Instant,
    val timeEnded: Instant?,
    val diff: Duration
)
