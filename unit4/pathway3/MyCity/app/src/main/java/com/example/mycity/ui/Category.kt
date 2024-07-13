package com.example.mycity.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.data.CityCategory
import com.example.mycity.data.Coffees
import com.example.mycity.data.Place

@Composable
fun CategoryScreen(
    modifier: Modifier,
    onClick: (Place) -> Unit = {},
    places: List<Place>
) {
    Log.d("CategoryScreen", "places: ${places.size}")
    LazyColumn {
        items(places.size) { index ->
            val place = places[index]
            ItemCard(
                itemName = place.name,
                itemImageRes = place.image,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { onClick(place) }
            )
        }
    }
}
