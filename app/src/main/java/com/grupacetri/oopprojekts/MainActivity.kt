package com.grupacetri.oopprojekts

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.grupacetri.oopprojekts.core.ui.UiState
import com.grupacetri.oopprojekts.core.ui.navigation.compose.Navigation
import com.grupacetri.oopprojekts.core.ui.theme.OOPProjektsTheme
import com.grupacetri.oopprojekts.featureSettings.domain.AllSettings
import com.grupacetri.oopprojekts.featureSettings.domain.AppSettings
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var appSettings: AppSettings

    private fun changeLanguage(language: AllSettings.Language) {

        if (language.language == AllSettings.Language.LanguageValue.System) {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.getEmptyLocaleList()
            )
            return
        }

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language.language.languageString)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appSettings = (this.application as OopProjektsApplication).settingsComponent.settings

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var theme: UiState<AllSettings.Theme> by mutableStateOf(UiState.Loading())
        var language: UiState<AllSettings.Language> by mutableStateOf(UiState.Loading())

        lifecycleScope.launch {
            // load theme
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                appSettings.theme
                    .onEach {
                        theme = it
                    }
                    .collect()
            }
        }
        lifecycleScope.launch {
            // load language
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                appSettings.language
                    .onEach {
                        language = it
                        if (it is UiState.Success) {
                            changeLanguage(it.data)
                        }
                    }
                    .collect()
            }
        }

        // keep splash screen on while theme is not loaded
        splashScreen.setKeepOnScreenCondition {
            when (theme) {
                is UiState.Loading -> true
                else -> false
            }
                    ||
            when (language) {
                is UiState.Loading -> true
                else -> false
            }
        }

        setContent {
            OOPProjektsTheme(
                // if theme is not loaded, default (will not show as behind splash screen)
                theme.let {
                    when (it) {
                        is UiState.Loading -> AllSettings.Theme()
                        is UiState.Success -> it.data
                    }
                }
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}