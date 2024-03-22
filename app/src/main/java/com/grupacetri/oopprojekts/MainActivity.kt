package com.grupacetri.oopprojekts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.grupacetri.oopprojekts.core.ui.navigation.composables.Navigation
import com.grupacetri.oopprojekts.ui.theme.OOPProjektsTheme

// Application Entry Point - opening the app starts here
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OOPProjektsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ui goes here - set up Navigation in different file
                    Navigation()
                }
            }
        }
    }
}