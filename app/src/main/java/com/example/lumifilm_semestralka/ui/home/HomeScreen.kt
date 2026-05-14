package com.example.lumifilm_semestralka.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.ui.Screen
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.R

@Composable
fun HomeScreen(navController: NavController, repository: MovieRepository) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))

    val uiState by viewModel.uiState.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D2D))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            stringResource(R.string.h_uvitanie),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            stringResource(R.string.h_vyber_zanra),
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFFFFFFF)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(genres) { genre ->
                FilterChip(
                    selected = selectedGenre == genre,
                    onClick = { viewModel.onGenreSelected(genre) },
                    label = { Text(genre.first, color = Color(0xFF000000)) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color(0xFF03fc94),
                        selectedContainerColor = Color(0xFF00b368),
                        labelColor = Color(0xFF000000),
                        selectedLabelColor = Color(0xFF000000)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getRecommendation() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.h_recomend), color = Color(0xFF000000))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is HomeUiState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.h_vyber_zaner_a_stlac),
                        color = Color(0xFFAAAAAA)
                    )
                }
            }
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF03fc94))
                }
            }
            is HomeUiState.Success -> {
                val movie = state.movie
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Column {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF000000)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row {
                                Text(
                                    text = movie.releaseDate.take(4),
                                    color = Color(0xFF555555)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "⭐ ${String.format("%.1f", movie.voteAverage)}",
                                    color = Color(0xFF000000)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = movie.overview.take(150) + "...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF333333)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    navController.navigate(Screen.Detail.createRoute(movie.id))
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    stringResource(R.string.h_zobraz_detail),
                                    color = Color(0xFF000000)
                                )
                            }
                        }
                    }
                }
            }
            is HomeUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}