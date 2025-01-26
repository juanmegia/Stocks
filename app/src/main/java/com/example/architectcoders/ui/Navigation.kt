package com.example.architectcoders.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.architectcoders.ui.detail.DetailScreen
import com.example.architectcoders.ui.home.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home" ){
        composable("home") {
            HomeScreen(onClick = {symbol ->
                navController.navigate("detail/${symbol}")
            })
        }
        composable("detail/{symbol}",
            arguments = listOf(navArgument("symbol"){type = NavType.StringType})
        ) {backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol")
            if (symbol != null) {
                DetailScreen(
                    symbol = symbol,
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }
}