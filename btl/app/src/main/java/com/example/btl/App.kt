package com.example.btl

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btl.ui.screen.ConnectSumScreen
import com.example.btl.ui.screen.DualPlayerScreen
import com.example.btl.ui.screen.MainScreen
import com.example.btl.ui.screen.NumberMatchScreen
import com.example.btl.ui.screen.TwoSeriesScreen

enum class Screen(val title: String) {
    Start("Start"),
    NumberMatching("Number Match"),
    TwoSeries("Two Series"),
    DualPlayer("Dual Player"),
    ConnectSum("Connect Sum")
}

@Composable
fun App(navController: NavHostController = rememberNavController()) {
    NavHost(
        startDestination = Screen.Start.name,
        navController = navController,
    ) {
        composable(route = Screen.Start.name) {
            MainScreen(
                onClickNumberMatching = { navController.navigate(Screen.NumberMatching.name) },
                onClickTwoSeries = { navController.navigate(Screen.TwoSeries.name) },
                onClickDualPlayer = { navController.navigate(Screen.DualPlayer.name) },
                onClickConnectSum = { navController.navigate(Screen.ConnectSum.name) }
            )
        }
        composable(route = Screen.NumberMatching.name) {
            NumberMatchScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
        composable(route = Screen.TwoSeries.name) {
            TwoSeriesScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
        }
        composable(route = Screen.DualPlayer.name) {
            DualPlayerScreen(onNavigateToMenuClick = { navController.navigate(Screen.Start.name) })
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