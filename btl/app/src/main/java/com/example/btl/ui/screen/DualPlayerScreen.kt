package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.Clock
import com.example.btl.ui.screen.Component.TopBar
import com.example.btl.viewModel.DualPlayerState
import com.example.btl.viewModel.DualPlayerViewModel
import kotlinx.coroutines.delay

@Composable
fun DualPlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: DualPlayerViewModel = viewModel(), onNavigateToMenuClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    var restartClock by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Dual Player",
                onNavigateToMenuClick = onNavigateToMenuClick
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
                targetSum = state.total ?: 0,
                correct = state.correctCount,
                isRestartClock = if (restartClock) {
                    restartClock = false
                    true
                } else false,
            ) {
                viewModel.finishGame()
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                NumberInput(
                    modifier = Modifier.weight(3f),
                    state = state,
                    onValueChange = {
                        viewModel.updateDualPlayerState(
                            first = it.toIntOrNull(),
                            second = state.second,
                            total = state.total,
                            isCorrect = state.isCorrect
                        )
                    },
                    onFocusLost = {
                        viewModel.checkAnswer()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Icon(
                imageVector = Icons.Default.Add, // Use default add icon
                contentDescription = "Plus",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                NumberInput(
                    modifier = Modifier.weight(3f),
                    state = state,
                    str = "Player 2",
                    onValueChange = {
                        viewModel.updateDualPlayerState(
                            first = state.first,
                            second = it.toIntOrNull(),
                            total = state.total,
                            isCorrect = state.isCorrect
                        )
                    },
                    onFocusLost = {
                        viewModel.checkAnswer()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.checkAnswer()
            }) {
                Text(text = "Submit")
            }
        }
        if (state.isCorrect != null) {
            Result(
                isCorrect = state.isCorrect!!,
                onRestart = {
                    viewModel.newQuestion()
                }
            )
        }
        if (state.isFinished) {
            FinalScoreDialog(
                correct = state.correctCount,
                onPlayAgain = {
                    viewModel.reset()
                    restartClock = true
                },
                onExit = onNavigateToMenuClick
            )
        }
    }
}

@Composable
private fun GameInfo(
    targetSum: Int,
    correct: Int,
    modifier: Modifier = Modifier,
    isRestartClock: Boolean = false,
    finishGame: () -> Unit = {}
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
                if (min == 2 && sec == 0) {
                    finishGame()
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Correct: $correct",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun NumberInput(
    modifier: Modifier = Modifier,
    state: DualPlayerState,
    str: String = "Player 1",
    onFocusLost: () -> Unit = {},
    onValueChange: (String) -> Unit = { _ -> },
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.clip(MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(3f)
                .clickable { focusRequester.requestFocus() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = str,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp),
            )
            NumberInputField(
                modifier = Modifier,
                value = state.first?.toString() ?: "",
                onValueChange = {
                    onValueChange(it)
                },
                isDelete = state.isCorrect != null,
                focusRequester = focusRequester,
                onFocusLost = onFocusLost,
            )
        }
    }
}

@Composable
private fun NumberInputField(
    modifier: Modifier = Modifier,
    maxDigits: Int = 2,
    value: String = "",
    isDelete: Boolean = false,
    focusRequester: FocusRequester = FocusRequester(),
    onFocusLost: () -> Unit = {},
    onValueChange: (String) -> Unit = { _ -> },
) {
    var text by remember { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.length <= maxDigits && newValue.all { it.isDigit() }) {
                text = newValue
                onValueChange(newValue)
                if (newValue.length == maxDigits) {
                    focusManager.clearFocus() // Xóa focus khi nhập đủ 2 chữ số
                }
            }
        },
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
                if (!it.isFocused) {
                    onFocusLost()
                }
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (text.isEmpty() && !isFocused) {
                    Text(
                        text = "Enter number",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                innerTextField()
            }
        }
    )

    if (isDelete) {
        text = ""
    }
}

@Composable
private fun Result(
    isCorrect: Boolean,
    onRestart: () -> Unit,
) {
    var showResult by remember { mutableStateOf(true) }

    // Hiển thị AlertDialog và tự động đóng sau khoảng thời gian khi showResult là true
    LaunchedEffect(showResult) {
        if (showResult) {
            delay(1500) // Hiển thị thông báo trong khoảng thời gian đã định
            showResult = false
            onRestart()
        }
    }

    if (showResult) {
        AlertDialog(
            onDismissRequest = { showResult = false },
            title = { Text("Result") },
            text = { Text(if (isCorrect) "Correct!" else "Incorrect!") },
            confirmButton = {
            }
        )
    }
}

@Composable
private fun FinalScoreDialog(
    correct: Int, onPlayAgain: () -> Unit,
    onExit: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onExit() },
        title = { Text(text = "Game Finished") },
        text = {
            Column {
                Text(text = "Your correct answers: $correct")
                Text(text = "Time: 2:00")
            }
        },
        confirmButton = {
            Button(onClick = { onPlayAgain() }) {
                Text("Play Again")
            }
        },
        dismissButton = {
            TextButton(onClick = { onExit() }) {
                Text("Menu")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun DualRowsScreenPreview() {
    DualPlayerScreen()
}
