package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DualRowsScreen(modifier: Modifier = Modifier, onNavigateToMenuClick: () -> Unit = {}) {
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

        Text(text = "Sum: 0 = ", modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Player 1")
                TextField(
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Enter a number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Icon(
                imageVector = Icons.Default.Add, // Use default add icon
                contentDescription = "Plus",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Player 2")
                TextField(
                    value = "",
                    onValueChange = { },
                    label = { Text(text = "Enter a number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Button to submit numbers
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


@Composable
@Preview(showBackground = true)
fun NumberInputChallengeScreenPreview() {
    DualRowsScreen()
}