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
fun RecommendedPlaceScreen(modifier: Modifier) {
    Text(text = "RecommendedPlaceScreen", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Click me")
    }
}
