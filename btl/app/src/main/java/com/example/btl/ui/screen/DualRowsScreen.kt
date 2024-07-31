package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.viewModel.DualRowsViewModel

@Composable
fun DualRowsScreen(
    modifier: Modifier = Modifier,
    viewModel: DualRowsViewModel = viewModel(), onNavigateToMenuClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sum: ${state.total} = ",
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Player 1")
                NumberInputField(
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
                Text(text = "Player 2")
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

    TextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.length <= maxDigits && newValue.all { it.isDigit() }) {
                text = newValue
                onValueChange(newValue)
            }
        },
        keyboardActions = KeyboardActions(
            onDone = { /* Handle done action */ }
        ),
        placeholder = { Text("Enter number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .border(0.dp, Color.Transparent)
            .padding(8.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun NumberInputChallengeScreenPreview() {
    DualRowsScreen()
}
