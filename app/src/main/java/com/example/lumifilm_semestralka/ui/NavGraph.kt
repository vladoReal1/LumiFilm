package com.example.lumifilm_semestralka.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.ui.detail.DetailScreen
import com.example.lumifilm_semestralka.ui.home.HomeScreen
import com.example.lumifilm_semestralka.ui.mylist.MojList
import com.example.lumifilm_semestralka.ui.search.SearchScreen
import com.example.lumifilm_semestralka.ui.settings.SettingsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    repository: MovieRepository,
    modifier: Modifier = Modifier

) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier

    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            SearchScreen(
                navController = navController,
                repository = repository
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(
                movieId = movieId,
                navController = navController,
                repository = repository
            )
        }
        composable(Screen.MyList.route) {
            MojList(
                navController = navController,
                repository = repository
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

    }
}