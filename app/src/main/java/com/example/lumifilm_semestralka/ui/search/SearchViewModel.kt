package com.example.lumifilm_semestralka.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Možné stavy obrazovky vyhľadávania
sealed class SearchUiState {
    object Idle : SearchUiState()        // Čaká na vstup
    object Loading : SearchUiState()     // Načítava výsledky
    data class Success(val movies: List<Movie>) : SearchUiState()  // Výsledky načítané
    data class Error(val message: String) : SearchUiState()        // Chyba
}

// AI assisted: ViewModel pre obrazovku vyhľadávania
class SearchViewModel(private val repository: MovieRepository) : ViewModel() {

    // Stav obrazovky - UI ho sleduje
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    // Aktuálny text v search bare
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    // Zavolá sa keď používateľ píše do search baru
    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    // Zavolá sa keď používateľ stlačí hľadaj
    fun searchMovies() {
        if (_query.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val movies = repository.searchMovies(_query.value)
                _uiState.value = SearchUiState.Success(movies)
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error("Nastala chyba pri hľadaní")
            }
        }
    }
}