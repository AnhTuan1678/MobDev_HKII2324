package com.example.btl.ui.screen.Component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.btl.algorithms.Node
import com.example.btl.data.NumberState


@Composable
fun GridNumber(
    modifier: Modifier = Modifier,
    numbers: List<NumberState>,
    selected: Int?,
    column: Int = 5,
    path: List<Node>? = null,
    onClick: (Int) -> Unit,
) {
    Box {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(column),
                contentPadding = PaddingValues(4.dp),
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


        if (path != null && path.size >= 2) {
            val lineColor = MaterialTheme.colorScheme.error
            val lineWeight = 12f

            Canvas(modifier = modifier.fillMaxWidth()) {
                val cellSize =
                    (size.width - 4.dp.toPx()) / column // Bằng chiều ngang của ô cộng với padding
                val offsetPx = 2.dp.toPx()

                for (i in 0 until path.size - 1) {
                    val start = path[i]
                    val end = path[i + 1]

                    // Tính toán tọa độ
                    val startX = start.y * cellSize - cellSize / 2 + offsetPx
                    val startY = start.x * cellSize - cellSize / 2 + offsetPx
                    val endX = end.y * cellSize - cellSize / 2 + offsetPx
                    val endY = end.x * cellSize - cellSize / 2 + offsetPx

                    drawLine(
                        color = lineColor, // Màu của đường kẻ
                        start = Offset(x = startX, y = startY),
                        end = Offset(x = endX, y = endY),
                        strokeWidth = lineWeight
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GridNumberPreview() {
//    numbers: [NumberState(number=3, isMatched=true, index=0, x=0, y=0), NumberState(number=4, isMatched=false, index=1, x=0, y=1), NumberState(number=1, isMatched=false, index=2, x=0, y=2), NumberState(number=4, isMatched=false, index=3, x=0, y=3), NumberState(number=4, isMatched=false, index=4, x=0, y=4), NumberState(number=1, isMatched=false, index=5, x=1, y=0), NumberState(number=3, isMatched=false, index=6, x=1, y=1), NumberState(number=3, isMatched=false, index=7, x=1, y=2), NumberState(number=3, isMatched=false, index=8, x=1, y=3), NumberState(number=1, isMatched=false, index=9, x=1, y=4), NumberState(number=2, isMatched=false, index=10, x=2, y=0), NumberState(number=1, isMatched=false, index=11, x=2, y=1), NumberState(number=4, isMatched=false, index=12, x=2, y=2), NumberState(number=4, isMatched=false, index=13, x=2, y=3), NumberState(number=1, isMatched=false, index=14, x=2, y=4), NumberState(number=3, isMatched=true, index=15, x=3, y=0), NumberState(number=3, isMatched=false, index=16, x=3, y=1), NumberState(number=3, isMatched=false, index=17, x=3, y=2), NumberState(number=3, isMatched=false, index=18, x=3, y=3), NumberState(number=3, isMatched=false, index=19, x=3, y=4), NumberState(number=2, isMatched=false, index=20, x=4, y=0), NumberState(number=2, isMatched=false, index=21, x=4, y=1), NumberState(number=1, isMatched=false, index=22, x=4, y=2), NumberState(number=2, isMatched=false, index=23, x=4, y=3), NumberState(number=2, isMatched=false, index=24, x=4, y=4)]
    val numbers: List<NumberState> = listOf(
        NumberState(3, true, 0, 0, 0),
        NumberState(4, false, 1, 0, 1),
        NumberState(1, false, 2, 0, 2),
        NumberState(4, false, 3, 0, 3),
        NumberState(4, false, 4, 0, 4),
        NumberState(1, false, 5, 1, 0),
        NumberState(3, false, 6, 1, 1),
        NumberState(3, false, 7, 1, 2),
        NumberState(3, false, 8, 1, 3),
        NumberState(1, false, 9, 1, 4),
        NumberState(2, false, 10, 2, 0),
        NumberState(1, false, 11, 2, 1),
        NumberState(4, false, 12, 2, 2),
        NumberState(4, false, 13, 2, 3),
        NumberState(1, false, 14, 2, 4),
        NumberState(3, true, 15, 3, 0),
        NumberState(3, false, 16, 3, 1),
        NumberState(3, false, 17, 3, 2),
        NumberState(3, false, 18, 3, 3),
        NumberState(3, false, 19, 3, 4),
        NumberState(2, false, 20, 4, 0),
        NumberState(2, false, 21, 4, 1),
        NumberState(1, false, 22, 4, 2),
        NumberState(2, false, 23, 4, 3),
        NumberState(2, false, 24, 4, 4),
    )
//    path: [Node(x=1, y=1, parent=null, direction=null, g=0, h=0), Node(x=1, y=0, parent=Node(x=1, y=1, parent=null, direction=null, g=0, h=0), direction=2, g=1, h=4), Node(x=2, y=0, parent=Node(x=1, y=0, parent=Node(x=1, y=1, parent=null, direction=null, g=0, h=0), direction=2, g=1, h=4), direction=1, g=3, h=3), Node(x=3, y=0, parent=Node(x=2, y=0, parent=Node(x=1, y=0, parent=Node(x=1, y=1, parent=null, direction=null, g=0, h=0), direction=2, g=1, h=4), direction=1, g=3, h=3), direction=1, g=4, h=2), Node(x=4, y=0, parent=Node(x=3, y=0, parent=Node(x=2, y=0, parent=Node(x=1, y=0, parent=Node(x=1, y=1, parent=null, direction=null, g=0, h=0), direction=2, g=1, h=4), direction=1, g=3, h=3), direction=1, g=4, h=2), direction=1, g=5, h=1), Node(x=4, y=1, parent=Node(x=4, y=0, parent=Node(x=3, y=0, parent=Node(x=2, y=0, parent=Node(x=1, y=0, parent=Node(x=1, y=1, parent=null, direction=null, g=0, h=0), direction=2, g=1, h=4), direction=1, g=3, h=3), direction=1, g=4, h=2), direction=1, g=5, h=1), direction=3, g=7, h=0)]
    val path: List<Node> = listOf(
        Node(1, 1, null, null, 0, 0),
        Node(1, 0, Node(1, 1, null, null, 0, 0), 2, 1, 4),
        Node(2, 0, Node(1, 0, Node(1, 1, null, null, 0, 0), 2, 1, 4), 1, 3, 3),
        Node(3, 0, Node(2, 0, Node(1, 0, Node(1, 1, null, null, 0, 0), 2, 1, 4), 1, 3, 3), 1, 4, 2),
        Node(
            4,
            0,
            Node(
                3,
                0,
                Node(2, 0, Node(1, 0, Node(1, 1, null, null, 0, 0), 2, 1, 4), 1, 3, 3),
                1,
                4,
                2
            ),
            1,
            5,
            1
        ),
        Node(
            4,
            1,
            Node(
                4,
                0,
                Node(
                    3,
                    0,
                    Node(2, 0, Node(1, 0, Node(1, 1, null, null, 0, 0), 2, 1, 4), 1, 3, 3),
                    1,
                    4,
                    2
                ),
                1,
                5,
                1
            ),
            3,
            7,
            0
        ),
    )
    GridNumber(
        numbers = numbers,
        selected = null,
        column = 5,
        path = path,
        onClick = {}
    )
}
