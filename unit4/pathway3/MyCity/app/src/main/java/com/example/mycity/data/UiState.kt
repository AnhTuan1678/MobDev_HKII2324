package com.example.mycity.data

import androidx.annotation.DrawableRes
import com.example.mycity.R

data class UiState(
    val category: CityCategory? = null,
    val place: Place? = null,
)

data class Place(
    val name: String,
    @DrawableRes val image: Int,
    val description: String,
    val location: String
)
data class CityCategory(
    val name: String,
    @DrawableRes val image: Int,
    val places: List<Place>
)
