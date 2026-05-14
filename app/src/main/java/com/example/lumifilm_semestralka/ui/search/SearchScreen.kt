package com.example.lumifilm_semestralka.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.ui.Screen
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.R
@Composable
fun SearchScreen(
    navController: NavController,
    repository: MovieRepository
) {
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(repository)
    )

    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            stringResource(R.string.s_hladanie),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(13.dp))

        // Search bar
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text(stringResource(R.string.s_dajteNaz)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                Button(onClick = { viewModel.searchMovies() }) {
                    Text(stringResource(R.string.s_hladaj_button))
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Obsah podľa stavu
        when (val state = uiState) {
            is SearchUiState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.s_zadaj_nazov),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            is SearchUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchUiState.Success -> {
                if (state.movies.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.s_nic_sa_nenaslo), color = Color(0xFFFF5555))
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.movies) { movie ->
                            MovieResultItem(
                                movie = movie,
                                onClick = {
                                    navController.navigate(
                                        Screen.Detail.createRoute(movie.id)
                                    )
                                }
                            )
                        }
                    }
                }
            }
            is SearchUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

// Jeden riadok výsledku vyhľadávania
@Composable
fun MovieResultItem(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Plagát
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .width(60.dp)
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Info o filme
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.releaseDate.take(4), // Len rok
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⭐ ${String.format("%.1f", movie.voteAverage)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}