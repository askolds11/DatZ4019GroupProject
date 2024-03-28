package com.grupacetri.oopprojekts.featureSecondScreen.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.grupacetri.oopprojekts.core.di.DomainScope
import com.grupacetri.oopprojekts.core.di.SingletonScope
import com.grupacetri.oopprojekts.featureExample.domain.ExampleUseCases
import me.tatarka.inject.annotations.Inject

@Inject
@DomainScope
class SecondViewModel(
    private val exampleUseCases: ExampleUseCases // inject exampleUseCases
): ViewModel() {
    // state for the UI (SecondScreen).
    val state = SecondScreenState()

    init {
        Log.d("Test", "${exampleUseCases.hashCode()}")
    }

    // all screen events get handled here
    fun onEvent(event: SecondScreenEvent) {
        // this must be exhaustive - if you delete one of the items (lines), you'll see
        // that it shows an error and won't let you compile
        when (event) {
            is SecondScreenEvent.UpdateText -> updateText(event.newValue)
        }
    }

    private fun updateText(newValue: String) {
        // update the text value
        // this causes the screen update
        state.inputText.value = newValue
    }
}