package com.example.lumifilm_semestralka.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.lumifilm_semestralka.domain.model.WatchStatus
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.R


// Obrazovka detailu filmu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    navController: NavController,
    repository: MovieRepository
) {
    val viewModel: DetailViewModel = viewModel(
        factory = DetailViewModelFactory(repository, movieId)
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color(0xFF2D2D2D),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.d_title), color = Color(0xFFFFFFFF)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.d_spat),
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2D2D2D)
                )
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFF2D2D2D)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF03fc94))
                }
            }
            is DetailUiState.Success -> {
                val movie = state.movie
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2D2D2D))
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Plagát
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Názov
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Rok a hodnotenie
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = movie.releaseDate.take(4),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFAAAAAA)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "⭐ ${String.format("%.1f", movie.voteAverage)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFFFFFF)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tlačidlá na pridanie do zoznamu
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            stringResource(R.string.d_chcem_pozriet) to WatchStatus.WANT_TO_WATCH,
                            stringResource(R.string.d_pozrene) to WatchStatus.WATCHED,
                            stringResource(R.string.d_oblubene)  to WatchStatus.FAVOURITE
                        ).forEach { (label, status) ->
                            Button(
                                onClick = { viewModel.updateWatchStatus(status) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (movie.watchStatus == status)
                                        Color(0xFF03fc94)
                                    else Color(0xFF3D3D3D)
                                )
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (movie.watchStatus == status)
                                        Color(0xFF000000)
                                    else Color(0xFFFFFFFF)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Popis
                    Text(
                        stringResource(R.string.d_popis),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF03fc94)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movie.overview.ifEmpty { stringResource(R.string.d_popis_nieje_dost) },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFCCCCCC)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Poznámka
                    Text(
                        stringResource(R.string.d_moja_poznamka) ,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF03fc94)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.note,
                        onValueChange = { viewModel.updateNote(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(R.string.d_pridanie_pozn), color = Color(0xFF888888)) },
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFFFFFFFF),
                            unfocusedTextColor = Color(0xFFFFFFFF),
                            focusedBorderColor = Color(0xFF03fc94),
                            unfocusedBorderColor = Color(0xFF555555)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.saveNote() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.d_ulozit_poznamku) , color = Color(0xFF000000))
                    }
                }
            }
            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2D2D2D)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message, color = Color(0xFFFF5555))
                }
            }
        }
    }
}