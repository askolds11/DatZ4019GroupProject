package com.grupacetri.oopprojekts.featureExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
class ExampleViewModel(
    private val exampleUseCases: ExampleUseCases
) : ViewModel() {
    val state = ExampleScreenState()

    val exampleListFlow: SharedFlow<Unit> = exampleUseCases.getList()
            .map {
                state.exampleList.clear()
                state.exampleList.addAll(it)
                return@map //Unit
            }.shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000)
            )

    fun onEvent(event: ExampleScreenEvent) {
        when (event) {
            is ExampleScreenEvent.Delete -> delete(event.id)
            is ExampleScreenEvent.Save -> save()
            is ExampleScreenEvent.UpdateText -> updateText(event.newValue)
        }
    }

    private fun delete(id: Long) {
        exampleUseCases.delete(id)
    }

    private fun save() {
        exampleUseCases.add(state.inputText.value)
    }

    private fun updateText(newValue: String) {
        state.inputText.value = newValue
    }
}