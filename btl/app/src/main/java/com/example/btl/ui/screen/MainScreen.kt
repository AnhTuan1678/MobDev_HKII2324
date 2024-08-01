package com.example.btl.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.btl.Screen
import com.example.btl.ui.screen.Component.TopBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onClickNumberMatching: () -> Unit = {},
    onClickDualSelect: () -> Unit = {},
    onClickInput: () -> Unit = {},
    onClickLink: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar(title = "Main Menu")
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Item(
                onClick = onClickNumberMatching,
                str = Screen.NumberMatch.title
            )
            Spacer(modifier = Modifier.height(16.dp))
            Item(
                onClick = onClickDualSelect,
                str = Screen.DualRows.title
            )
            Spacer(modifier = Modifier.height(16.dp))
            Item(
                onClick = onClickInput,
                str = Screen.SumPairs.title
            )
            Spacer(modifier = Modifier.height(16.dp))
            Item(
                onClick = onClickLink,
                str = Screen.ConnectSum.title
            )
        }
    }
}

@Composable
fun Item(modifier: Modifier = Modifier, onClick: () -> Unit = {}, str: String) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .width(300.dp)
            .height(50.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = str,
            fontSize = 24.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen()
}