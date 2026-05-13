package com.example.lumifilm_semestralka.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lumifilm_semestralka.data.repository.MovieRepository
import com.example.lumifilm_semestralka.domain.model.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class StatsUiState(
    val watchedCount: Int = 0,
    val wantToWatchCount: Int = 0,
    val favouritesCount: Int = 0
)

class SettingsViewModel(private val repository: MovieRepository) : ViewModel() {

    val stats: StateFlow<StatsUiState> = combine(
        repository.getMoviesByStatus(WatchStatus.WATCHED),
        repository.getMoviesByStatus(WatchStatus.WANT_TO_WATCH),
        repository.getMoviesByStatus(WatchStatus.FAVOURITE)
    ) { watched, wantToWatch, favourites ->
        StatsUiState(
            watchedCount = watched.size,
            wantToWatchCount = wantToWatch.size,
            favouritesCount = favourites.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StatsUiState())
}

class SettingsViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SettingsViewModel(repository) as T
    }
}