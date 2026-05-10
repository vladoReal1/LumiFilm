package com.example.lumifilm_semestralka.ui.mylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.ui.Screen

// AI assisted: Obrazovka osobného zoznamu filmov
@Composable
fun MojList(navController: NavController, repository: MovieRepository) {
    val viewModel: MyListViewModel = viewModel(factory = MyListViewModelFactory(repository))

    val lists = listOf(
        "Chcem pozrieť" to viewModel.wantToWatch.collectAsState().value,
        "Pozrené" to viewModel.watched.collectAsState().value,
        "Obľúbené" to viewModel.favourites.collectAsState().value
    )

    var selectedTab by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Môj zoznam",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        TabRow(selectedTabIndex = selectedTab) {
            lists.forEachIndexed { index, (title, _) ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, style = MaterialTheme.typography.labelMedium) }
                )
            }
        }

        val currentList = lists[selectedTab].second

        if (currentList.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Žiadne filmy v tejto kategórii",
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(currentList) { movie ->
                    MovieListItem(
                        movie = movie,
                        onClick = { navController.navigate(Screen.Detail.createRoute(movie.id)) },
                        onDelete = { viewModel.deleteMovie(movie) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier.width(60.dp).height(90.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(movie.title, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium)
                Text(movie.releaseDate.take(4),
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (movie.userNote.isNotEmpty())
                    Text(movie.userNote, maxLines = 1,
                        style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Vymazať",
                    tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}