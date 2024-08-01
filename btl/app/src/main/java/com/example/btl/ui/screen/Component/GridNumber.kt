package com.example.btl.ui.screen.Component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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