package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConnectSumScreen(modifier: Modifier = Modifier, onNavigateToMenuClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = onNavigateToMenuClick,
            Modifier.background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.padding(end = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Connect Numbers",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Grid of numbers
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 0 until 4) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (j in 0 until 6) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .border(1.dp, Color.Black)
                                .clickable {
                                    // Logic to select or connect numbers
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = (i * 6 + j).toString()) // Replace with your number logic
                        }
                    }
                }
            }
        }

        // Button to submit the connected numbers
        Button(
            onClick = {
                // Logic to check if the sum is correct
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Check Sum")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectSumScreenPreview() {
    ConnectSumScreen()
}
