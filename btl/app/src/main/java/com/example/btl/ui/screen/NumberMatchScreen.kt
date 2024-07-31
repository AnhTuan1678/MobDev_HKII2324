package com.example.btl.ui.screen

import android.app.Activity
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val numbers by viewModel.numbers.collectAsState(emptyList())
    val selected by viewModel.selected.collectAsState(null)
    val total by viewModel.targetSum.collectAsState(0)
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
            text = "Target sum: $total",
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
        FinalScoreDialog(
            score = 100,
            onPlayAgain = { viewModel.reset() },
            onExit = { }
        )
        Log.d("NumberMatchScreen", "Game finished")
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
        },
        title = { Text(text = "Final Score: $score") },
        text = { Text(text = "Congratulations!") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = { onExit() }
            ) {
                Text(text = "Exit")
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = "Play Again")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun NumberMatchingScreenPreview() {
    NumberMatchScreen(viewModel = NumberMatchViewModel())
}
