package com.example.lumifilm_semestralka.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.domain.model.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// AI assisted: ViewModel pre obrazovku osobného zoznamu
class MyListViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _wantToWatch = MutableStateFlow<List<Movie>>(emptyList())
    val wantToWatch: StateFlow<List<Movie>> = _wantToWatch

    private val _watched = MutableStateFlow<List<Movie>>(emptyList())
    val watched: StateFlow<List<Movie>> = _watched

    private val _favourites = MutableStateFlow<List<Movie>>(emptyList())
    val favourites: StateFlow<List<Movie>> = _favourites

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab

    init {
        loadLists()
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
    private fun loadLists() {
        viewModelScope.launch {
            repository.getMoviesByStatus(WatchStatus.WANT_TO_WATCH).collect {
                _wantToWatch.value = it
            }
        }
        viewModelScope.launch {
            repository.getMoviesByStatus(WatchStatus.WATCHED).collect {
                _watched.value = it
            }
        }
        viewModelScope.launch {
            repository.getMoviesByStatus(WatchStatus.FAVOURITE).collect {
                _favourites.value = it
            }
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }
}

class MyListViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}