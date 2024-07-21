package com.example.mycity.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass

enum class Screen {
    City,
    RecommendedPlace,
    Category
}

@Suppress("NAME_SHADOWING")
@Composable
fun MyCityApp(modifier: Modifier, windowSize: WindowSizeClass? = null, viewModel: CityViewModel) {
    // Nav
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.City.name
    )
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            AppBar(modifier = Modifier)
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        NavHost(
            navController = navController,
            startDestination = Screen.City.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.City.name) {
                CityScreen(
                    modifier = modifier,
                    onClick = {
                        viewModel.changeCategory(it)
                        navController.navigate(Screen.Category.name)
                    }
                )
            }
            composable(Screen.Category.name) {
                CategoryScreen(
                    modifier = modifier,
                    onClick = {
                        viewModel.changePlace(it)
                        navController.navigate(Screen.RecommendedPlace.name)
                    },
                    places = uiState.category?.places ?: emptyList()
                )
            }
            composable(Screen.RecommendedPlace.name) {
                RecommendedPlaceScreen(modifier = modifier, place = uiState.place!!)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier, currentScreen: Screen? = null) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "MyCity")
        },
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun MyCityAppPreview() {
    val viewModel = CityViewModel()
    MyCityApp(Modifier, null, viewModel)
}