package com.example.btl.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.btl.ui.screen.Component.GridNumber
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.SumPairsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SumPairsScreen(
    modifier: Modifier = Modifier,
    onNavigateToMenuClick: () -> Unit = {},
    viewModel: SumPairsViewModel = viewModel()
) {
    val firstNumbers by viewModel.firstNumbers.collectAsState()
    val secondNumbers by viewModel.secondNumbers.collectAsState()
    val targetSum by viewModel.targetSum.collectAsState(0)
    val firstSelect by viewModel.firstSelect.collectAsState(null)
    val secondSelect by viewModel.secondSelect.collectAsState(null)
    val isFinished by viewModel.isFinished.collectAsState(false)

    Scaffold(
        topBar = {
            TopBar(title = "Sum Pairs", onNavigateToMenuClick = onNavigateToMenuClick)
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            GridNumber(numbers = firstNumbers, selected = firstSelect) {
                viewModel.selectFirstNumber(it)
            }
            Spacer(modifier = Modifier.weight(1f))
            Card {
                Text(
                    text = "Total Requirement $targetSum",
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            GridNumber(numbers = secondNumbers, selected = secondSelect) {
                viewModel.selectSecondNumber(it)
            }
            Spacer(modifier = Modifier.padding(16.dp))
        }

        if (isFinished) {
            FinalScoreDialog(
                onPlayAgain = { viewModel.reset(size = 12, total = 14) },
                onExit = onNavigateToMenuClick
            )
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
    SumPairsScreen()
}