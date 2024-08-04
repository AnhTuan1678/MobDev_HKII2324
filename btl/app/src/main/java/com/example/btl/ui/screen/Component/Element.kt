package com.example.btl.ui.screen.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.btl.data.NumberState

@Composable
fun Element(
    numberState: NumberState,
    modifier: Modifier = Modifier,
    isClicked: Boolean = false,
    showMatched: Boolean = true,
    onClick: () -> Unit = {},
) {
    if (numberState.isMatched) {
        if (showMatched) Box(modifier = modifier) { }
    } else {
        Box(
            modifier = modifier
                .background(
                    if (isClicked) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() }
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = numberState.number.toString(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}