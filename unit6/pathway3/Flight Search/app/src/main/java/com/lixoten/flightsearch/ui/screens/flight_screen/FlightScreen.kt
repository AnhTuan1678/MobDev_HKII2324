package com.lixoten.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lixoten.flightsearch.NavigationDestination
import com.lixoten.flightsearch.R

object FlightScreenDestination : NavigationDestination {
    override val route = "flight_screen"
    override val titleRes = R.string.app_name
    const val codeArg = "code"
    val routeWithArgs = "$route/{$codeArg}"

}

@Composable
fun FlightScreen() {
    val viewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState()

    Column {
        FlightResults(
            departureAirport = uiState.value.departureAirport,
            destinationList = uiState.value.destinationList,
        )
    }
}