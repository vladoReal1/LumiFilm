package com.example.lumifilm_semestralka.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lumifilm_semestralka.ui.detail.DetailScreen
import com.example.lumifilm_semestralka.ui.home.HomeScreen
import com.example.lumifilm_semestralka.ui.mylist.MojList
import com.example.lumifilm_semestralka.ui.search.SearchScreen
import com.example.lumifilm_semestralka.ui.settings.SettingsScreen

// AI assisted: Navigačný graf celej aplikácie
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(movieId = movieId, navController = navController)
        }
        composable(Screen.MyList.route) {
            MojList(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}