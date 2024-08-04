package com.example.btl.ui.screen.Component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun Clock(
    modifier: Modifier = Modifier,
    isRestart: Boolean = false,
    onTimeUpdate: (minutes: Int, seconds: Int, showColon: Boolean) -> Unit = { _, _, _ -> },
) {
    var seconds by remember { mutableIntStateOf(0) }
    var minutes by remember { mutableIntStateOf(0) }
    var showColon by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            seconds++
            if (seconds == 60) {
                seconds = 0
                minutes++
            }
            onTimeUpdate(minutes, seconds, showColon)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500L)
            showColon = !showColon
        }
    }

    LaunchedEffect(isRestart) {
        if (isRestart) {
            seconds = 0
            minutes = 0
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = String.format("%02d%s%02d", minutes, if (showColon) ":" else " ", seconds),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ClockPreview() {
    Clock(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp),
    )
}