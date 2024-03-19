package com.grupacetri.oopprojekts.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun Flow<Unit>.collectAsStateWithLifecycle(): State<Unit> {
    return this.collectAsStateWithLifecycle(initialValue = Unit)
}