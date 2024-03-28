package com.grupacetri.oopprojekts.featureExample.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupacetri.oopprojekts.core.di.DomainScope
import com.grupacetri.oopprojekts.core.di.SingletonScope
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class ExampleViewModel(
    private val exampleUseCases: ExampleUseCases // inject exampleUseCases
) : ViewModel() {
    // state for the UI (ExampleScreen).
    val state = ExampleScreenState()

    init {
        Log.d("Test", "${exampleUseCases.hashCode()}")
    }

    // exampleUseCases.getList() is a flow from the database (table).
    // that means that the flow emits the new List, if there is an update/insert/deletion
    // in the database (table). in this case this when this emission happens, the code in .map {}
    // gets executed.
    // shareIn means that the code gets executed only if there is an active subscriber to the flow
    // this ensures that for the flow to be collected, the screen needs to be open - this saves resources.
    // e.g. you could have the app minimized and a background service update the database
    // without the shareIn this code would be executed, which is not needed
    val exampleListFlow: SharedFlow<Unit> = exampleUseCases.getList()
            .map {
                state.exampleList.clear()
                state.exampleList.addAll(it)
                return@map //Unit
            }.shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000)
            )

    // all screen events get handled here
    fun onEvent(event: ExampleScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is ExampleScreenEvent.Delete -> delete(event.id)
            is ExampleScreenEvent.Save -> save()
            is ExampleScreenEvent.UpdateText -> updateText(event.newValue)
        }
    }

    private fun delete(id: Long) {
        // delete from database
        exampleUseCases.delete(id)
    }

    private fun save() {
        // insert in database
        exampleUseCases.add(state.inputText.value)
    }

    private fun updateText(newValue: String) {
        // update the text value
        // this causes the screen update
        state.inputText.value = newValue
    }
}