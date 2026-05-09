package com.example.lumifilm_semestralka.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val genreIds: String,
    val isMovie: Boolean = true,
    val watchStatus: String = "NONE",
    val userRating: Int = 0,
    val userNote: String = ""
)