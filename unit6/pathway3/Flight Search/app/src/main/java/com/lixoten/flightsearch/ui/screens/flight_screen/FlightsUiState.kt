package com.lixoten.flightsearch.ui.screens.flight_screen

import com.lixoten.flightsearch.model.Airport
import com.lixoten.flightsearch.model.Favorite

data class FlightsUiState(
    val code: String = "",
    val destinationList: List<Airport> = emptyList(),
    val departureAirport: Airport = Airport(),
)
