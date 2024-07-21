package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.data.Coffees
import com.example.mycity.data.Place

@Composable
fun RecommendedPlaceScreen(modifier: Modifier, place: Place) {
    Column {
        Image(
            painter = painterResource(place.image),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = place.name,
//            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = place.description,
//            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Book a table")
        }
    }
}
