package com.example.lumifilm_semestralka.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.domain.model.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val movie: Movie, val note: String) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

// AI assisted: ViewModel pre detail filmu
class DetailViewModel(
    private val repository: MovieRepository,
    private val movieId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            try {
                // Najprv skús načítať z lokálnej DB
                val localMovie = repository.getMovieById(movieId)
                if (localMovie != null) {
                    _uiState.value = DetailUiState.Success(localMovie, localMovie.userNote)
                } else {
                    // Načítaj detail priamo z TMDB API podľa ID
                    val movie = repository.getMovieDetail(movieId)
                    if (movie != null) {
                        _uiState.value = DetailUiState.Success(movie, "")
                    } else {
                        _uiState.value = DetailUiState.Error("Film sa nenašiel")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error("Nastala chyba: ${e.message}")
            }
        }
    }

    fun updateWatchStatus(status: WatchStatus) {
        val current = _uiState.value
        if (current is DetailUiState.Success) {
            viewModelScope.launch {
                val updated = current.movie.copy(watchStatus = status)
                repository.saveMovie(updated)
                _uiState.value = DetailUiState.Success(updated, current.note)
            }
        }
    }

    fun updateNote(note: String) {
        val current = _uiState.value
        if (current is DetailUiState.Success) {
            _uiState.value = DetailUiState.Success(current.movie, note)
        }
    }

    fun saveNote() {
        val current = _uiState.value
        if (current is DetailUiState.Success) {
            viewModelScope.launch {
                val updated = current.movie.copy(userNote = current.note)
                repository.saveMovie(updated)
                _uiState.value = DetailUiState.Success(updated, current.note)
            }
        }
    }
}

class DetailViewModelFactory(
    private val repository: MovieRepository,
    private val movieId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}