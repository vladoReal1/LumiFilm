package com.example.lumifilm_semestralka.data.repository

import com.example.lumifilm_semestralka.data.local.LumiFilmDatabase
import com.example.lumifilm_semestralka.data.local.MovieEntity
import com.example.lumifilm_semestralka.data.remote.MovieDto
import com.example.lumifilm_semestralka.data.remote.RetrofitInstance
import com.example.lumifilm_semestralka.domain.model.Movie
import com.example.lumifilm_semestralka.domain.model.WatchStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// AI assisted: Repository spája lokálnu DB a API
class MovieRepository(private val database: LumiFilmDatabase) {

    private val dao = database.movieDao()

    // ---- SIEŤOVÉ VOLANIA ----

    suspend fun searchMovies(query: String) = fetchMovies {
        RetrofitInstance.api.searchMovies(RetrofitInstance.apiKey, query).results
    }

    suspend fun getMoviesByGenre(genreId: Int) = fetchMovies {
        RetrofitInstance.api.getMoviesByGenre(RetrofitInstance.apiKey, genreId).results
    }

    // Pomocná funkcia – zabalí každé API volanie do try/catch
    private suspend fun fetchMovies(block: suspend () -> List<com.example.lumifilm_semestralka.data.remote.MovieDto>): List<Movie> {
        return try {
            block().map { it.toMovie() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ---- LOKÁLNA DATABÁZA ----

    fun getAllSavedMovies(): Flow<List<Movie>> =
        dao.getAllMovies().map { it.map { e -> e.toMovie() } }

    fun getMoviesByStatus(status: WatchStatus): Flow<List<Movie>> =
        dao.getMoviesByStatus(status.name).map { it.map { e -> e.toMovie() } }

    suspend fun saveMovie(movie: Movie) = dao.insertMovie(movie.toEntity())
    suspend fun updateMovie(movie: Movie) = dao.updateMovie(movie.toEntity())
    suspend fun deleteMovie(movie: Movie) = dao.deleteMovie(movie.toEntity())


    private fun MovieEntity.toMovie() = Movie(
        id = id, title = title, overview = overview,
        posterPath = posterPath, releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds.split(",").mapNotNull { it.trim().toIntOrNull() },
        isMovie = isMovie, watchStatus = WatchStatus.valueOf(watchStatus),
        userRating = userRating, userNote = userNote
    )

    private fun Movie.toEntity() = MovieEntity(
        id = id, title = title, overview = overview,
        posterPath = posterPath, releaseDate = releaseDate,
        voteAverage = voteAverage, genreIds = genreIds.joinToString(","),
        isMovie = isMovie, watchStatus = watchStatus.name,
        userRating = userRating, userNote = userNote
    )

    suspend fun getMovieById(movieId: Int): Movie? {
        return dao.getMovieById(movieId)?.toMovie()
    }

    suspend fun getMovieDetail(movieId: Int): Movie? {
        return try {
            RetrofitInstance.api.getMovieDetail(movieId, RetrofitInstance.apiKey).toMovie()
        } catch (e: Exception) {
            null
        }
    }
}