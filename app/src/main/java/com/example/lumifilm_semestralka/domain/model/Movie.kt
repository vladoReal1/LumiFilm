package com.example.lumifilm_semestralka.domain.model


data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val isMovie: Boolean = true,
    val watchStatus: WatchStatus = WatchStatus.NONE,
    val userRating: Int = 0,
    val userNote: String = ""
)

enum class WatchStatus {
    NONE,
    WANT_TO_WATCH,
    WATCHED,
    FAVOURITE
}