package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.Clock
import com.example.btl.ui.screen.Component.ClockSettings
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.MarathonViewModel
import com.example.btl.viewModel.Question
import kotlinx.coroutines.delay

@Composable
fun MarathonScreen(
    modifier: Modifier = Modifier,
    viewModel: MarathonViewModel = viewModel(),
    onNavigateToMenuClick: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsState()
    var restartClock by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(title = "Marathon", onNavigateToMenuClick = onNavigateToMenuClick)
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GameInfo(
                modifier = Modifier.padding(16.dp),
                correct = uiState.correctCount,
                onTimeUpdate = { time ->
                    if (time == 0) {
                        viewModel.nextQuestion()
                        restartClock = true
                    }
                },
                isRestartClock = if (restartClock) {
                    restartClock = false
                    true
                } else false,
                isPauseClock = uiState.isFinished
            )
            Question(
                uiState.questions[uiState.currentQuestionIndex]
            )
            SelectableNumbers(
                uiState.questions[uiState.currentQuestionIndex].selectableNumbers
            ) { selectedNumber ->
                viewModel.checkAnswer(selectedNumber)
            }
        }
        if (uiState.isCorrect == true) {
            true.Result {
                viewModel.nextQuestion()
                restartClock = true
            }
        }else if (uiState.isCorrect == false) {
            viewModel.finish()
        }

        if (uiState.isFinished) {
            FinalScoreDialog(
                correctAnswer = uiState.correctCount,
                onPlayAgain = { viewModel.reset() },
                onExit = onNavigateToMenuClick
            )
        }
    }
}

@Composable
fun Question(question: Question, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.inversePrimary,
                MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(text = "Question")
        Text(
            text = "${question.addend1 ?: "__"} + ${question.addend2 ?: "__"} = ${question.sum ?: "__"}",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun GameInfo(
    modifier: Modifier = Modifier,
    correct: Int,
    onTimeUpdate: (Int) -> Unit = {},
    isRestartClock: Boolean = false,
    isPauseClock: Boolean = false
) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.inversePrimary,
                MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Correct: $correct",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        Clock(
            isRestart = isRestartClock, settings = ClockSettings(0, 5, true), isPause = isPauseClock
        ) { min, sec, _ ->
            onTimeUpdate(min * 60 + sec)
        }
    }
}

@Composable
fun SelectableNumbers(selectableNumbers: List<Int>, onClick: (Int) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        selectableNumbers.forEach {
            SelectableNumber(
                it,
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                onClick = onClick
            )
        }
    }
}

@Composable
fun SelectableNumber(number: Int, modifier: Modifier = Modifier, onClick: (Int) -> Unit = {}) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(number) }
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun Boolean.Result(
    onRestart: () -> Unit,
) {
    var showResult by remember { mutableStateOf(true) }

    // Hiển thị AlertDialog và tự động đóng sau khoảng thời gian khi showResult là true
    LaunchedEffect(showResult) {
        if (showResult) {
            delay(500) // Hiển thị thông báo trong khoảng thời gian đã định
            showResult = false
            onRestart()
        }
    }

    if (showResult) {
        AlertDialog(
            onDismissRequest = { showResult = false },
            title = {
                Text(text = if (this) "Correct!" else "Incorrect!")
            },
            confirmButton = {
            }
        )
    }
}

@Composable
private fun FinalScoreDialog(
    modifier: Modifier = Modifier,
    correctAnswer: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Game Finished") },
        text = {
            Text(text = "Your correct answer: $correctAnswer")
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
fun MarathonScreenPreview() {
    MarathonScreen()
}