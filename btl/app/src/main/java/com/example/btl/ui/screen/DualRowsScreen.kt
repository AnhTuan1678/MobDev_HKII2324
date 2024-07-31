package com.example.btl.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberInputChallengeScreen(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = onClick) {
            Text(text = "Menu")
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Number Input Challenge")
    }
}

@Composable
@Preview(showBackground = true)
fun NumberInputChallengeScreenPreview() {
    NumberInputChallengeScreen()
}