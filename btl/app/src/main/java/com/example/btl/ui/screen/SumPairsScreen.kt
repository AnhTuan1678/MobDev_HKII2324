package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SumPairsScreen(modifier: Modifier = Modifier, onNavigateToMenuClick: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize()) {
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

        Series(arr = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Sum Pairs")
        }
        Series(arr = listOf(11, 12, 13, 14, 15, 16, 17, 18, 19, 20))
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun Series(arr: List<Int>) {
    LazyRow {
        items(arr.size) { index ->
            Element(number = arr[index])
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DualPlayerNumberSelectionScreenPreview() {
    SumPairsScreen()
}