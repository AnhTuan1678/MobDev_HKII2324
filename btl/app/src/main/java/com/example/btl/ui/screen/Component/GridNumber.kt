package com.example.btl.ui.screen.Component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btl.data.NumberState


@Composable
fun GridNumber(
    modifier: Modifier = Modifier,
    numbers: List<NumberState>,
    selected: Int?,
    column: Int = 5,
    onClick: (Int) -> Unit,
) {
    Surface(
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(column),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(numbers.size) { index ->
                Element(
                    numberState = numbers[index],
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp),
                    onClick = { onClick(index) },
                    isClicked = selected == index
                )
            }
        }
    }
}

@Composable
fun LineNumber(
    modifier: Modifier = Modifier,
    numbers: List<NumberState>,
    selected: Int?,
    onClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    // Thực hiện hiệu ứng cuộn liên tục
    LaunchedEffect(Unit) {
        while (true) {
            scrollState.animateScrollTo(
                scrollState.maxValue,
                animationSpec = tween(
                    durationMillis = 1000 * numbers.size,
                    easing = LinearEasing
                )
            )
            scrollState.animateScrollTo(
                0,
                animationSpec = tween(
                    durationMillis = 1000 * numbers.size,
                    easing = LinearEasing
                )
            )
        }
    }

    // Hiển thị hàng số và xử lý nhấn vào phần tử
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .horizontalScroll(scrollState)
            .padding(4.dp),
    ) {
        numbers.forEachIndexed { index, numberState ->
            Element(
                numberState = numberState,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                onClick = { onClick(index) },
                isClicked = selected == index
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LineNumberPreview() {
    var numbers: List<NumberState> = emptyList()
    for (i in 0..24) {
        numbers += NumberState((1..9).random(), false, i)
    }
    LineNumber(
        numbers = numbers,
        selected = null,
        onClick = {}
    )
}