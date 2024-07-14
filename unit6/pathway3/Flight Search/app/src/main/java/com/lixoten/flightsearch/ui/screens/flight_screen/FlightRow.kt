package com.lixoten.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lixoten.flightsearch.data.MockData
import com.lixoten.flightsearch.ui.screens.search.AirportRow

@Composable
fun FlightRow(
    modifier: Modifier = Modifier,
    departureAirportCode: String,
    departureAirportName: String,
    destinationAirportCode: String,
    destinationAirportName: String,
) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Column(
                modifier = modifier.weight(10f)
            ) {
                Column {
                    Text(
                        text = "Depart",
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(code = departureAirportCode, name = departureAirportName)
                    Text(
                        text = "Arrival",
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(code = destinationAirportCode, name = destinationAirportName)
                }
            }
        }
    }
}


@Preview
@Composable
fun FlightRowPreview() {
    val mock = MockData
    FlightRow(
        departureAirportCode = mock.airports[1].code,
        departureAirportName = mock.airports[1].name,
        destinationAirportCode = mock.airports[2].code,
        destinationAirportName = mock.airports[2].name,
    )
}

