package com.example.superheroes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.HeroesRepository

val heroes = HeroesRepository.heroes

@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .height(72.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = hero.name),
                    style = MaterialTheme.typography.displaySmall,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(id = hero.description),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            val shape = RoundedCornerShape(8.dp)
            Image(
                painter = painterResource(id = hero.image),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape)
            )
        }
    }
}

@Composable
fun HeroesScreen(modifier: Modifier = Modifier) {
    LazyColumn (
        modifier = modifier
    ){
        items(heroes) { hero ->
            HeroCard(hero = hero, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroCardPreview() {
    HeroesScreen(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
}