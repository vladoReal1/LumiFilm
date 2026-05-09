package com.example.lumifilm_semestralka.ui


// AI assisted: Definícia navigačných ciest obrazoviek
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
    object MyList : Screen("mylist")
    object Settings : Screen("settings")
}