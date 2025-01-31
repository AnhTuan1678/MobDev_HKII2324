package com.example.mycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycity.ui.CityViewModel
import com.example.mycity.ui.MyCityApp
import com.example.mycity.ui.theme.MyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCityTheme {
                val windowSize = currentWindowAdaptiveInfo().windowSizeClass
                val viewModel = CityViewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCityApp(Modifier.padding(innerPadding), windowSize, viewModel)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyCityApp() {
    MyCityTheme {
        MyCityApp(Modifier.fillMaxSize(), null, CityViewModel())
    }
}