package com.example.lumifilm_semestralka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.lumifilm_semestralka.data.local.LumiFilmDatabase
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.ui.NavGraph
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lumifilm_semestralka.ui.Screen
import com.example.lumifilm_semestralka.ui.theme.MojaLumiTheme
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.R
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vytvorenie databázy a repository
        val database = Room.databaseBuilder(
            applicationContext,
            LumiFilmDatabase::class.java,
            "lumifilm_database"
        ).build()

        val repository = MovieRepository(database)

        setContent {
            MojaLumiTheme {
                val navController = rememberNavController()

                Scaffold(
                    containerColor = Color(0xFF2D2D2D),
                    bottomBar = {
                        NavigationBar {
                            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.nav_home)) },
                                label = { Text(stringResource(R.string.nav_home)) },
                                selected = currentRoute == Screen.Home.route,
                                onClick = { navController.navigate(Screen.Home.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.nav_search)) },
                                label = { Text(stringResource(R.string.nav_search)) },
                                selected = currentRoute == Screen.Search.route,
                                onClick = { navController.navigate(Screen.Search.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = stringResource(R.string.nav_list)) },
                                label = { Text(stringResource(R.string.nav_list)) },
                                selected = currentRoute == Screen.MyList.route,
                                onClick = { navController.navigate(Screen.MyList.route) }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.nav_profile)) },
                                label = { Text(stringResource(R.string.nav_profile)) },
                                selected = currentRoute == Screen.Settings.route,
                                onClick = { navController.navigate(Screen.Settings.route) }
                            )
                        }
                    }
                ) { paddingValues ->
                    NavGraph(
                        navController = navController,
                        repository = repository,
                        modifier = Modifier.padding(paddingValues).background(Color(0xFF2D2D2D))
                    )
                }
            }
        }
    }
}