package com.example.btl.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.LineNumber
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.TwoSeriesViewModel

@Composable
fun TwoSeriesScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenuClick: () -> Unit = {},
    viewModel: TwoSeriesViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = "Two Series",
                onNavigateToMenuClick = onNavigateToMenuClick
            )
        }
    ) {
        if (uiState.isFinished) {
            FinalScoreDialog(
                onPlayAgain = { viewModel.reset(size = 12, total = 14) },
                onExit = onNavigateToMenuClick
            )
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))

                LineNumber(numbers = uiState.firstNumbers, selected = uiState.firstSelect) {
                    viewModel.selectFirstNumber(it)
                    Log.d("TwoSeriesViewModel", "selectFirstNumber: ${uiState.firstNumbers[it]}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Select: ${if (uiState.firstSelect != null) uiState.firstNumbers[uiState.firstSelect!!].number else "None"}")

                Spacer(modifier = Modifier.weight(2f))
                Card {
                    Text(
                        text = "Total Requirement ${uiState.targetSum}",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(2f))
                Text(text = "Select: ${if (uiState.secondSelect != null) uiState.secondNumbers[uiState.secondSelect!!].number else "None"}")

                Spacer(modifier = Modifier.height(16.dp))
                LineNumber(numbers = uiState.secondNumbers, selected = uiState.secondSelect) {
                    viewModel.selectSecondNumber(it)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
private fun FinalScoreDialog(
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Final") },
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

@Preview(showBackground = true)
@Composable
fun DualPlayerNumberSelectionScreenPreview() {
    TwoSeriesScreen()
}