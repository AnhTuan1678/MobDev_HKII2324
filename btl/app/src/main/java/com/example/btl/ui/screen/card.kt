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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Element(number: Int, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    var isClicked by remember { mutableStateOf(false) } // Theo dõi trạng thái nhấp chuột

    Box(
        modifier = modifier
            .size(60.dp) // Đặt kích thước để tạo hình tròn
            .background(
                if (isClicked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                CircleShape
            )
            .clickable {
                isClicked = !isClicked // Chuyển đổi trạng thái khi nhấp chuột
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Element(number = 1, modifier = Modifier, onClick = {})
    }
}