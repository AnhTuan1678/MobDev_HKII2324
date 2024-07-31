package com.example.btl.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.viewModel.NumberMatchViewModel

@Composable
fun NumberMatchScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenuClick: () -> Unit = {},
    viewModel: NumberMatchViewModel = viewModel()
) {
    val numbers by viewModel.numbers.observeAsState(emptyList())
    val selected by viewModel.selected.observeAsState(null)
    val elementSize = 60.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        IconButton(
            onClick = onNavigateToMenuClick,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Target sum: ${viewModel.targetSum.value}",
            style = MaterialTheme.typography.titleLarge
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, color = Color.Black)
        ) {
            for ((index, numberState) in numbers.withIndex()) {
                Element(
                    numberState = numberState,
                    modifier = Modifier
                        .offset(
                            x = (index % 5) * (elementSize + 8.dp),
                            y = (index / 5) * (elementSize + 8.dp)
                        ),
                    onClick = { viewModel.selectNumber(index) },
                    isClicked = selected == index
                )
            }
        }
    }
    if (viewModel.isGameFinished()) {
        Log.d("NumberMatchScreen", "Game finished")
    }
}

//
//@Composable
//fun Element(
//    number: NumberState,
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit = {}
//) {
//    Box(
//        modifier = modifier
//            .background(Color.LightGray)
//            .clickable { onClick() }
//            .border(1.dp, Color.Black)
//    ) {
//        Text(
//            text = number.number.toString(),
//            modifier = Modifier.fillMaxSize(),
//            color = Color.Black
//        )
//    }
//}

@Composable
@Preview(showBackground = true)
fun NumberMatchingScreenPreview() {
    NumberMatchScreen(viewModel = NumberMatchViewModel())
}
