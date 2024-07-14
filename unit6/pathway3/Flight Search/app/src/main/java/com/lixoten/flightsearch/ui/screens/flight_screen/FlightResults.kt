package com.lixoten.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lixoten.flightsearch.data.MockData
import com.lixoten.flightsearch.model.Airport

@Composable
fun FlightResults(
    modifier: Modifier = Modifier,
    departureAirport: Airport,
    destinationList: List<Airport>,
) {
    Column {
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(destinationList, key = { it.id }) { item ->
                FlightRow(
                    departureAirportCode = departureAirport.code,
                    departureAirportName = departureAirport.name,
                    destinationAirportCode = item.code,
                    destinationAirportName = item.name
                )
            }
        }
    }
}

@Preview
@Composable
fun FlightResultsPreview() {
    val mockData = MockData.airports

    FlightResults(
        departureAirport = mockData[0],
        destinationList = mockData,
    )
}