package com.example.architectcoders.ui

import DetailViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.architectcoders.ui.detail.DetailScreen
import com.example.architectcoders.ui.home.HomeScreen
import com.example.architectcoders.ui.home.HomeViewModel


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
    val viewModel: HomeViewModel = viewModel()

    NavHost(navController = navController, startDestination = NavScreen.Home.route ){
        composable(NavScreen.Home.route) {

            HomeScreen(onClick = {symbol ->
                navController.navigate(NavScreen.Detail.createRoute(symbol))
            }, viewModel = viewModel)
        }
        composable(NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.SYMBOL.key){type = NavType.StringType})
        ) {backStackEntry ->
            val symbol = backStackEntry.arguments?.getString(NavArgs.SYMBOL.key)
            val viewModel: DetailViewModel = viewModel()
            if (symbol != null) {
                DetailScreen(
                    symbol = symbol,
                    onBack = {navController.popBackStack()},
                    viewModel = viewModel

                )
            }
        }
    }
}