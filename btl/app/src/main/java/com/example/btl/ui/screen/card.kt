package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btl.viewModel.NumberState

@Composable
fun Element(
    numberState: NumberState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isClicked: Boolean = false
) {
    if (numberState.isMatched) return

    Box(
        modifier = modifier
            .size(60.dp) // Đặt kích thước để tạo hình tròn
            .background(
                if (isClicked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clickable {
                onClick()
            }
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = numberState.number.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Element(numberState = NumberState(5, false, 0), modifier = Modifier, onClick = {})
    }
}