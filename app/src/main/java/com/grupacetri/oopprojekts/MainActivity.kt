package com.grupacetri.oopprojekts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupacetri.oopprojekts.featureExample.di.ExampleComponent
import com.grupacetri.oopprojekts.featureExample.di.create
import com.grupacetri.oopprojekts.featureExample.ui.ExampleScreen
import com.grupacetri.oopprojekts.featureExample.ui.ExampleViewModel
import com.grupacetri.oopprojekts.ui.theme.OOPProjektsTheme

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
//                    Greeting("Android")
                    val test = (applicationContext as OopProjektsApplication).exampleComponent.exampleScreen
                    test()
                }
            }
        }
    }
}