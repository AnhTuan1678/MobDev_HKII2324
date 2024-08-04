package com.example.btl.ui.screen

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
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.GridNumber
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.NumberMatchViewModel

@Composable
fun NumberMatchScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenuClick: () -> Unit = {},
    viewModel: NumberMatchViewModel = viewModel(),
//    scaffoldState:
) {
    val uiState by viewModel.state.collectAsState()

    var row by remember { mutableIntStateOf(5) } // Số hàng
    var column by remember { mutableIntStateOf(5) } // Số cột

    Scaffold(
        topBar = {
            TopBar(
                title = "Number Match",
                onNavigateToMenuClick = onNavigateToMenuClick,
                action = {
                    SettingsButton(row = row, column = column, total = uiState.targetSum) { r, c, t ->
                        row = r
                        column = c
                        viewModel.reset(size = r * c, total = t)
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Card {
                Text(
                    text = "Target sum: ${uiState.targetSum}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card {
                Text(
                    text = "Score: ${uiState.score}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            GridNumber(
                numbers = uiState.numbers,
                selected = uiState.selected,
                column = column
            ) { it ->
                viewModel.selectNumber(it)
            }
            Spacer(modifier = Modifier.weight(1f))

            if (uiState.isFinished) {
                FinalScoreDialog(
                    score = uiState.score,
                    onPlayAgain = { viewModel.reset(size = row * column, total = uiState.targetSum) },
                    onExit = { onNavigateToMenuClick() }
                )
            }
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
    score: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
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
    NumberMatchScreen()
}
