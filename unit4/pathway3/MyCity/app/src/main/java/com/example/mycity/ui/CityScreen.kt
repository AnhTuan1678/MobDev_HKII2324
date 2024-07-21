package com.example.mycity.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.data.CityCategory
import com.example.mycity.data.city

@Composable
fun CityScreen(
    modifier: Modifier = Modifier,
    onClick: (CityCategory) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        city.forEach() { cityCategory ->
            ItemCard(
                itemName = cityCategory.name,
                itemImageRes = cityCategory.image,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { onClick(cityCategory) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityScreenPreview() {
    CityScreen()
}