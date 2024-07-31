package com.example.btl.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun NumberMatchingScreen(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val arr = Array(10) { i -> i }
    val randomPositions = arr.toList().shuffled()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val elementSize = 60.dp // Kích thước phần tử

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
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
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Menu")
        }
    }
}

fun getRandomPositions(size: Int, row: Int, col: Int): List<Int> {
    val arr = Array(size) { i -> i }
    return arr.toList().shuffled()
}

@Composable
@Preview(showBackground = true)
fun NumberMatchingScreenPreview() {
    NumberMatchingScreen()
}