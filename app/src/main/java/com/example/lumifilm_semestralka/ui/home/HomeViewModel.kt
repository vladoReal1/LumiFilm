package com.example.lumifilm_semestralka.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Žánre s ich TMDB ID
val genres = listOf(
    "Akcia" to 28,
    "Komédia" to 35,
    "Dráma" to 18,
    "Horor" to 27,
    "Sci-Fi" to 878,
    "Animovaný" to 16,
    "Thriller" to 53,
    "Romantika" to 10749
)

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val movie: Movie) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

// AI assisted: ViewModel pre domovskú obrazovku
class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _selectedGenre = MutableStateFlow(genres[0])
    val selectedGenre: StateFlow<Pair<String, Int>> = _selectedGenre

    fun onGenreSelected(genre: Pair<String, Int>) {
        _selectedGenre.value = genre
    }

    fun getRecommendation() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val movies = repository.getMoviesByGenre(_selectedGenre.value.second)
                val random = movies.randomOrNull()
                if (random != null) {
                    _uiState.value = HomeUiState.Success(random)
                } else {
                    _uiState.value = HomeUiState.Error("Žiadne filmy pre tento žáner")
                }
            }
            catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Nastala chyba")
            }
        }
    }
}

class HomeViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(repository) as T
    }
}