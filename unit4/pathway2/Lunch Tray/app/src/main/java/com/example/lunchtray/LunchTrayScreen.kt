/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.AccompanimentMenuScreen
import com.example.lunchtray.ui.CheckoutScreen
import com.example.lunchtray.ui.EntreeMenuScreen
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.ui.SideDishMenuScreen
import com.example.lunchtray.ui.StartOrderScreen

// TODO: Screen enum
enum class Screen(val title: String) {
    Start(title = "Start"),
    EntreeMenu(title = "Entree Menu"),
    SideDishMenu(title = "Side Dish Menu"),
    AccompanimentMenu(title = "Accompaniment Menu"),
    Checkout(title = "Checkout"),
}

// TODO: AppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String) {
    CenterAlignedTopAppBar(title = {
        Text(text = title)
    })
}


@Composable
fun LunchTrayApp(navController: NavHostController = rememberNavController()) {
    // TODO: Create Controller and initialization
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Start.name
    )

    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            // TODO: AppBar
            AppBar(title = currentScreen.title)
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        val modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(32.dp)
        val cancelButtonHandle: () -> Unit = {
            viewModel.resetOrder()
            navController.popBackStack(Screen.Start.name, inclusive = false)
        }

        // TODO: Navigation host
        NavHost(
            navController = navController,
            startDestination = Screen.Start.name
        ) {
            composable(Screen.Start.name) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        navController.navigate(Screen.EntreeMenu.name)
                    },
                    modifier = modifier
                )
            }
            composable(Screen.EntreeMenu.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = cancelButtonHandle,
                    onNextButtonClicked = {
                        navController.navigate(Screen.SideDishMenu.name)
                    },
                    onSelectionChanged = viewModel::updateEntree,
                    modifier = modifier
                )
            }
            composable(Screen.SideDishMenu.name) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = cancelButtonHandle,
                    onNextButtonClicked = {
                        navController.navigate(Screen.AccompanimentMenu.name)
                    },
                    onSelectionChanged = viewModel::updateSideDish,
                    modifier = modifier
                )
            }
            composable(Screen.AccompanimentMenu.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = cancelButtonHandle,
                    onNextButtonClicked = {
                        navController.navigate(Screen.Checkout.name)
                    },
                    onSelectionChanged = viewModel::updateAccompaniment,
                    modifier = modifier
                )
            }
            composable(Screen.Checkout.name) {
                CheckoutScreen(
                    orderUiState = uiState,
                    onNextButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(Screen.Start.name, inclusive = false)
                    },
                    onCancelButtonClicked = cancelButtonHandle,
                    modifier = modifier
                )
            }
        }
    }
}
