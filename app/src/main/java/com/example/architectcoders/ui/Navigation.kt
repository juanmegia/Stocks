package com.example.architectcoders.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.architectcoders.ui.detail.DetailScreen
import com.example.architectcoders.ui.home.HomeScreen


sealed class NavScreen(val route: String){
    data object Home : NavScreen("home")
    data object Detail: NavScreen("detail/{${NavArgs.SYMBOL.key}}"){
        fun createRoute(symbol: String) = "detail/$symbol"
    }
}

enum class NavArgs(val key: String){
    SYMBOL("symbol")
}
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.Home.route ){
        composable(NavScreen.Home.route) {
            HomeScreen(onClick = {symbol ->
                navController.navigate(NavScreen.Detail.createRoute(symbol))
            })
        }
        composable(NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.SYMBOL.key){type = NavType.StringType})
        ) {backStackEntry ->
            val symbol = backStackEntry.arguments?.getString(NavArgs.SYMBOL.key)
            if (symbol != null) {
                DetailScreen(
                    symbol = symbol,
                    onBack = {navController.popBackStack()}
                )
            }
        }
    }
}