package com.example.btl.ui.screen.Component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btl.data.NumberState
import kotlinx.coroutines.delay

@SuppressLint("FrequentlyChangedStateReadInComposition", "CoroutineCreationDuringComposition")
@Composable
fun LineNumber(
    modifier: Modifier = Modifier,
    numbers: List<NumberState>,
    selected: Int?,
    onClick: (Int) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    var scrollDirection by remember { mutableIntStateOf(1) } // 1 = to right, -1 = to left

    val elementSize = with(LocalDensity.current) { 100.dp.toPx() }
    val elementPadding = with(LocalDensity.current) { 4.dp.toPx() }
    val count = numbers.count { !it.isMatched }
    LaunchedEffect(Unit) {
        delay(1000)
        val needToScrollItems = count - lazyListState.layoutInfo.visibleItemsInfo.size + 1
        val scrollWidth = needToScrollItems * (elementSize + elementPadding)
        while (true) {
            lazyListState.animateScrollBy(
                value = scrollDirection * scrollWidth,
                animationSpec = tween(
                    durationMillis = 1000 * needToScrollItems,
                    easing = LinearEasing
                )
            )
            scrollDirection *= -1
        }
    }

    LazyRow(
        state = lazyListState,
        userScrollEnabled = false,
        modifier = modifier
    ) {
        items(numbers.size) { index ->
            val numberState = numbers[index]
            Element(
                numberState = numberState,
                modifier = Modifier
                    .height(100.dp)
                    .aspectRatio(1f)
                    .padding(4.dp),
                isClicked = selected == numberState.index,
                showMatched = false
            ) {
                onClick(numberState.index)
                Log.d("TwoSeriesViewModel", "selectFirstNumber: $numberState")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LineNumberPreview() {
    val numbers: List<NumberState> by remember {
        mutableStateOf(
            List(3) {
                NumberState((1..9).random(), false, it)
            }
        )
    }
    var count by remember { mutableIntStateOf(0) }
    Column {
        Text(text = "Count: $count")
        LineNumber(
            numbers = numbers,
            selected = null,
            onClick = { count++ }
        )
    }
}