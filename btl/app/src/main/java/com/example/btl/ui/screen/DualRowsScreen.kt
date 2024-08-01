package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.ui.screen.Component.BackButton
import com.example.btl.viewModel.DualRowsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DualRowsScreen(
    modifier: Modifier = Modifier,
    viewModel: DualRowsViewModel = viewModel(), onNavigateToMenuClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Number Match",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = { BackButton(onNavigateToMenuClick) })
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total Requirement: ${state.total}",
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Player 1",
                        textAlign = TextAlign.Center
                    )
                    NumberInputField(
                        modifier = Modifier,
                        value = state.first?.toString() ?: "",
                        onValueChange = {
                            viewModel.updateDualRowsState(
                                first = it.toIntOrNull(),
                                second = state.second,
                                total = state.total,
                                isCorrect = state.isCorrect
                            )
                            viewModel.checkAnswer()
                        })
                }

                Icon(
                    imageVector = Icons.Default.Add, // Use default add icon
                    contentDescription = "Plus",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Player 2",
                        textAlign = TextAlign.Center
                    )
                    NumberInputField(
                        value = state.second?.toString() ?: "",
                        onValueChange = {
                            viewModel.updateDualRowsState(
                                first = state.first,
                                second = it.toIntOrNull(),
                                total = state.total,
                                isCorrect = state.isCorrect
                            )
                            viewModel.checkAnswer()
                        })
                }
            }

            if (state.isCorrect) {
                Text(text = "Correct!", modifier = Modifier.padding(top = 16.dp))
                LocalFocusManager.current.clearFocus()
            }
        }
    }
}

@Composable
fun NumberInputField(
    modifier: Modifier = Modifier,
    maxDigits: Int = 2,
    value: String = "",
    onValueChange: (String) -> Unit = { _ -> },
) {
    var text by remember { mutableStateOf(value) }

    BasicTextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.length <= maxDigits && newValue.all { it.isDigit() }) {
                text = newValue
                onValueChange(newValue)
            }
        },
        modifier = modifier.background(Color.Transparent),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .border(0.dp, Color.Transparent)
                    .padding(8.dp)
            ) {
                if (text.isEmpty()) {
                    Text("Enter number", color = Color.Gray)
                }
                innerTextField()
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun NumberInputChallengeScreenPreview() {
    DualRowsScreen()
}
