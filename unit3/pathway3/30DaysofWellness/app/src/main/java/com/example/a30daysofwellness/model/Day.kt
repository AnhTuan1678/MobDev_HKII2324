package com.example.a30daysofwellness.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Day(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)
