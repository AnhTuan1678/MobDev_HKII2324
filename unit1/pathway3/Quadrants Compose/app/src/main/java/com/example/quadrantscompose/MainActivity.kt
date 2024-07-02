package com.example.quadrantscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quadrantscompose.ui.theme.QuadrantsComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuadrantsComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FourEqualPartsScreen()
                }
            }
        }
    }
}

@Composable
fun FourEqualPartsScreen() {
    Column {
        Row(Modifier.weight(1f)) {
            QuadrantScreen(
                Color(0xFFEADDFF),
                Modifier.weight(1f),
                "Text composable",
                "Displays text and follows the recommended Material Design guidelines."
            )
            QuadrantScreen(
                Color(0xFFD0BCFF),
                Modifier.weight(1f),
                "Image composable",
                "Creates a composable that lays out and draws a given Painter class object."
            )
        }
        Row(Modifier.weight(1f)) {
            QuadrantScreen(
                Color(0xFFB69DF8),
                Modifier.weight(1f),
                "Row composable",
                "A layout composable that places its children in a horizontal sequence."
            )
            QuadrantScreen(
                Color(0xFFF6EDFF),
                Modifier.weight(1f),
                "Column composable",
                "A layout composable that places its children in a vertical sequence."
            )
        }
    }
}

@Composable
fun QuadrantScreen(
    background: Color,
    modifier: Modifier = Modifier,
    cardTitle: String = "",
    cardContent: String = ""
) {
    Surface(
        color = background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.padding(16.dp)
        ) {
            Text(
                text = cardTitle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = cardContent,
                style = TextStyle.Default,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuadrantsComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            FourEqualPartsScreen()
        }
    }
}