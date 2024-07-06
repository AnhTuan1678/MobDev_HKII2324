package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

data object DataSource{
    val affirmations = listOf<Affirmation>(
        Affirmation(R.string.architecture, R.drawable.architecture),
        Affirmation(R.string.automotive, R.drawable.automotive),
        Affirmation(R.string.biology, R.drawable.biology),
        Affirmation(R.string.business, R.drawable.business),
        Affirmation(R.string.crafts, R.drawable.crafts),
        Affirmation(R.string.culinary, R.drawable.culinary),
        Affirmation(R.string.design, R.drawable.design),
        Affirmation(R.string.drawing, R.drawable.drawing),
        Affirmation(R.string.ecology, R.drawable.ecology),
        Affirmation(R.string.engineering, R.drawable.engineering),
        Affirmation(R.string.fashion, R.drawable.fashion),
        Affirmation(R.string.film, R.drawable.film),
        Affirmation(R.string.finance, R.drawable.finance),
        Affirmation(R.string.gaming, R.drawable.gaming),
        Affirmation(R.string.geology, R.drawable.geology),
        Affirmation(R.string.history, R.drawable.history),
        Affirmation(R.string.journalism, R.drawable.journalism),
        Affirmation(R.string.law, R.drawable.law),
        Affirmation(R.string.lifestyle, R.drawable.lifestyle),
        Affirmation(R.string.music, R.drawable.music),
        Affirmation(R.string.painting, R.drawable.painting),
        Affirmation(R.string.photography, R.drawable.photography),
        Affirmation(R.string.physics, R.drawable.physics),
        Affirmation(R.string.tech, R.drawable.tech)
    )
}