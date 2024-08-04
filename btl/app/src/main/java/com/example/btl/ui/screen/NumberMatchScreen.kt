package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.Clock
import com.example.btl.ui.screen.Component.GridNumber
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.NumberMatchViewModel

@Composable
fun NumberMatchScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenuClick: () -> Unit = {},
    viewModel: NumberMatchViewModel = viewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    var row by remember { mutableIntStateOf(5) } // Số hàng
    var column by remember { mutableIntStateOf(5) } // Số cột
    var restartClock by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = "Number Match",
                onNavigateToMenuClick = onNavigateToMenuClick,
                action = {
                    SettingsButton(
                        row = row,
                        column = column,
                        total = uiState.targetSum
                    ) { r, c, t ->
                        row = r
                        column = c
                        viewModel.reset(size = r * c, total = t)
                        restartClock = true
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameInfo(
                targetSum = uiState.targetSum,
                score = uiState.score,
                correct = uiState.correctCunt,
                isRestartClock = if (restartClock) {
                    restartClock = false
                    true
                } else false,
                onTimeUpdate = { t ->
                    if (!uiState.isFinished) {
                        time = t
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            GridNumber(
                numbers = uiState.numbers,
                selected = uiState.selected,
                column = column
            ) { it ->
                viewModel.selectNumber(it)
            }


        }

        if (uiState.isFinished) {
            FinalScoreDialog(
                score = uiState.score,
                time = time,
                onPlayAgain = {
                    viewModel.reset(
                        size = row * column,
                        total = uiState.targetSum
                    )
                    restartClock = true
                },
                onExit = { onNavigateToMenuClick() }
            )
        }
    }
}

@Composable
private fun GameInfo(
    modifier: Modifier = Modifier,
    targetSum: Int,
    score: Int,
    correct: Int,
    onTimeUpdate: (String) -> Unit = {},
    isRestartClock: Boolean = false
) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.inversePrimary,
                MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Target sum: $targetSum",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            Clock(isRestart = isRestartClock) { min, sec, _ ->
                onTimeUpdate("$min:$sec")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Score: $score",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Correct: $correct",
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
private fun SettingsButton(
    row: Int = 4,
    column: Int = 6,
    total: Int = 13,
    apply: (Int, Int, Int) -> Unit = { _, _, _ -> },
) {
    var showDialog by remember { mutableStateOf(false) }
    IconButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .padding(4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Menu",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }

    if (showDialog) {
        SettingsDialog(
            onDismiss = { showDialog = false },
            apply = apply,
            row = row,
            column = column,
            total = total
        )
    }

}

@Composable
private fun SettingsDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    row: Int,
    column: Int,
    total: Int,
    apply: (Int, Int, Int) -> Unit = { _, _, _ -> }
) {
    var tRow by remember { mutableIntStateOf(row) }
    var tColumn by remember { mutableIntStateOf(column) }
    var tTotal by remember { mutableIntStateOf(total) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Settings") },
        text = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Row: ")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { if (tRow > 4) tRow-- }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Decrease row"
                        )
                    }
                    Text(
                        text = tRow.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = { if (tRow < 6) tRow++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Column: ")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { if (tColumn > 4) tColumn-- }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Decrease column"
                        )
                    }
                    Text(
                        text = tColumn.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = { if (tColumn < 6) tColumn++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total: ")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { if (tTotal > 8) tTotal-- }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Decrease total"
                        )
                    }
                    Text(
                        text = tTotal.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    IconButton(onClick = { if (tTotal < 18) tTotal++ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                apply(tRow, tColumn, tTotal)
                onDismiss()
            }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel", color = Color.Black)
            }
        },
        modifier = modifier
    )
}

@Composable
private fun FinalScoreDialog(
    modifier: Modifier = Modifier,
    score: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
    time: String = ""
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Game Finished") },
        text = {
            Column {
                Text(text = "Your score: $score")
                Text(text = "Time: $time")
            }
        },
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
    NumberMatchScreen()
}
