package com.example.btl

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btl.ui.screen.ConnectSumScreen
import com.example.btl.ui.screen.DualRowsScreen
import com.example.btl.ui.screen.MainScreen
import com.example.btl.ui.screen.NumberMatchScreen
import com.example.btl.ui.screen.SumPairsScreen

enum class Screen(val title: String) {
    Start("Start"),
    NumberMatch("Number Match"),
    SumPairs("Sum Pairs"),
    DualRows("Dual Rows"),
    ConnectSum("Connect Sum")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController = rememberNavController()) {
    NavHost(
        startDestination = Screen.Start.name,
        navController = navController,
    ) {
        composable(route = Screen.Start.name) {
            MainScreen(
                onClickNumberMatching = { navController.navigate(Screen.NumberMatch.name) },
                onClickDualSelect = { navController.navigate(Screen.SumPairs.name) },
                onClickInput = { navController.navigate(Screen.DualRows.name) },
                onClickLink = { navController.navigate(Screen.ConnectSum.name) }
            )
        }
        composable(route = Screen.NumberMatch.name) {
            NumberMatchScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
        composable(route = Screen.SumPairs.name) {
            SumPairsScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
        composable(route = Screen.DualRows.name) {
            DualRowsScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
        composable(route = Screen.ConnectSum.name) {
            ConnectSumScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}