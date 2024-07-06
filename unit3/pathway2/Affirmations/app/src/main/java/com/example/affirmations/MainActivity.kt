package com.example.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.affirmations.data.DataSource
import com.example.affirmations.model.Affirmation
import com.example.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AffirmationsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AffirmationsApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp(modifier: Modifier) {
    val affirmations = remember { DataSource.affirmations }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(affirmations) { affirmation ->
            AffirmationCard(
                affirmation = affirmation
            )
        }
    }
}

@Composable
fun AffirmationCard(affirmation: Affirmation) {
    Card (){
        Row {
            val image = painterResource(id = affirmation.imageResourceId)
            Image(
                painter = image,
                contentDescription = stringResource(id = affirmation.stringResourceId),
                Modifier.size(68.dp)
            )
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp
                )
            ) {
                Text(
                    text = stringResource(id = affirmation.stringResourceId)
                        .replaceFirstChar { it.uppercase() },
                    modifier = Modifier.height(20.dp),
                )
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                    Text(
                        text = "123",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AffirmationsTheme {
        AffirmationsApp(Modifier)
    }
}