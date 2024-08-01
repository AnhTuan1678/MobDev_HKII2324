package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btl.viewModel.ConnectSumViewModel
import com.example.btl.viewModel.Node

@Composable
fun ConnectSumScreen(
    modifier: Modifier = Modifier,
    viewModel: ConnectSumViewModel = viewModel(),
    onNavigateToMenuClick: () -> Unit = {}
) {
    val nodes by viewModel.nodes.collectAsState()
    val total by viewModel.total.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = onNavigateToMenuClick,
            Modifier.background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.padding(end = 4.dp)
            )
        }

        Text(
            text = "Connect Numbers",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = "Total: $total",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Matrix(
            modifier = Modifier.weight(1f),
            row = 6,
            column = 4,
            onClick = { viewModel.selectNode(it) },
            nodes = nodes
        )
    }
}

@Composable
fun Matrix(
    modifier: Modifier = Modifier,
    row: Int = 4, //Số hàng
    column: Int = 6, //Số cột
    onClick: (Int) -> Unit = {},
    nodes: List<Node> = emptyList()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until row) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in 0 until column) {
                    val index = i * column + j
                    Element(
                        modifier = Modifier.weight(1f),
                        number = nodes[index]
                    ) {
                        onClick(index)
                    }
                }
            }
        }
    }
}

@Composable
private fun Element(modifier: Modifier = Modifier, number: Node, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(1.dp)
            .aspectRatio(1f)
            .border(1.dp, if (number.isHidden) Color.Red else Color.Black)
            .clickable {
                if (!number.isHidden) onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${number.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun ConnectSumScreenPreview() {
    ConnectSumScreen()
}
