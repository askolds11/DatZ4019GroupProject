package com.grupacetri.oopprojekts.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grupacetri.oopprojekts.OopProjektsApplication
import kotlinx.coroutines.flow.Flow

// We make flows that only emit Unit. It updates the state, and as such we do not care about it's value.
@Composable
fun Flow<Unit>.collectAsStateWithLifecycle(): State<Unit> {
    return this.collectAsStateWithLifecycle(initialValue = Unit)
}

// To get Application instance
@Composable
fun getThisApplication(): OopProjektsApplication {
    return LocalContext.current.applicationContext as OopProjektsApplication
}

fun Boolean.toLong(): Long {
    return if (this) 1 else 0
}