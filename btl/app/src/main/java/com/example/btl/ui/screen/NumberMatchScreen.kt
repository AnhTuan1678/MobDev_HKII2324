package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun NumberMatchScreen(modifier: Modifier = Modifier, onNavigateToMenuClick: () -> Unit = {}) {
    val arr = Array(10) { i -> i }
    val randomPositions = arr.toList().shuffled()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val elementSize = 60.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
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

        Spacer(modifier = Modifier.padding(16.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, color = Color.Black)
        ) {
            for (i in arr.indices) {
                Element(
                    number = arr[i],
                    modifier = Modifier
                        .offset(
                            x = (randomPositions[i] % 5) * (elementSize + 8.dp), // Thay đổi khoảng cách
                            y = (randomPositions[i] / 5) * (elementSize + 8.dp)  // Thay đổi khoảng cách
                        )
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NumberMatchingScreenPreview() {
    NumberMatchScreen()
}