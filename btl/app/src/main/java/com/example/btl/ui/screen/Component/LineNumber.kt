package com.example.btl.ui.screen.Component

import android.annotation.SuppressLint
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btl.data.NumberState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DELAY_TIME = 5L
private const val SCROLL_DX = 2F

@SuppressLint("FrequentlyChangedStateReadInComposition", "CoroutineCreationDuringComposition")
@Composable
fun LineNumber(
    modifier: Modifier = Modifier,
    numbers: List<NumberState>,
    selected: Int?,
    onClick: (Int) -> Unit,
) {
    var itemListState by remember { mutableStateOf(numbers) }
    var lastIndex by remember { mutableIntStateOf(0) }
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        modifier = modifier
    ) {
        items(
            itemListState.size
        ) { index ->
            val numberState = itemListState[index]
            Element(
                numberState = numberState,
                modifier = Modifier
                    .height(100.dp)
                    .aspectRatio(1f)
                    .padding(4.dp),
                onClick = {
                    onClick(numberState.index)
                    lastIndex = numberState.index
                },
                isClicked = selected == numberState.index
            )

            if (itemListState[index] == itemListState.last()) {
                val currentList = itemListState
                val secondPart = currentList.subList(0, lazyListState.firstVisibleItemIndex)
                val firstPart =
                    currentList.subList(lazyListState.firstVisibleItemIndex, currentList.size)

                rememberCoroutineScope().launch {
                    lazyListState.scrollToItem(
                        0,
                        lazyListState.firstVisibleItemScrollOffset - SCROLL_DX.toInt(),
                    )
                }

                itemListState = firstPart + secondPart
            }
        }
    }

    LaunchedEffect(Unit) {
        autoScroll(lazyListState)
    }

    LaunchedEffect(numbers) {
        val temp = numbers[lastIndex]
        itemListState = numbers.map {
            if (it.index == temp.index) {
                it.copy(isMatched = true)
            } else {
                it
            }
        }
    }
}

private tailrec suspend fun autoScroll(lazyListState: LazyListState) {
    lazyListState.scroll(MutatePriority.PreventUserInput) {
        scrollBy(SCROLL_DX)
    }
    delay(DELAY_TIME)
    autoScroll(lazyListState)
}

@Preview(showBackground = true)
@Composable
fun LineNumberPreview() {
    val numbers: List<NumberState> by remember {
        mutableStateOf(
            List(10) {
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